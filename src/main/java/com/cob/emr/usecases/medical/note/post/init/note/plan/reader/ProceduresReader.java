package com.cob.emr.usecases.medical.note.post.init.note.plan.reader;

import com.cob.emr.model.medical.note.types.initial.evaluation.plan.Procedures;
import com.cob.emr.service.mapper.FieldMappingService;
import com.cob.emr.usecases.medical.note.post.init.note.plan.ReadPlanCategorisesUseCase;
import com.cob.emr.usecases.medical.note.post.init.note.plan.mappers.ProcedureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProceduresReader {
    @Autowired
    ReadPlanCategorisesUseCase readPlanCategorisesUseCase;
    @Autowired
    FieldMappingService fieldMappingService;

    private static final String PROCEDURES = "Procedures";
    private final String SECTION = "plan";
    public Map<String, List<String>> read(Procedures proceduresPlanOfCare) throws IOException {
        Map<String, List<String>> yamlProcedures = readPlanCategorisesUseCase.get(PROCEDURES);
        Map<String, List<String>> selectedProcedures = new HashMap<>();

        for (Map.Entry<String, List<String>> entry : yamlProcedures.entrySet()) {
            String categoryDisplayName = entry.getKey();

            // 1️⃣ Get the key (field name in Procedures object) for the category
            String categoryFieldName = fieldMappingService.lookupKeyByDisplay(SECTION, PROCEDURES, categoryDisplayName)
                    .orElseThrow(() -> new IllegalArgumentException("Field not found: " + categoryDisplayName));

            Object categoryValue = ProcedureMapper.getFieldValue(proceduresPlanOfCare, categoryFieldName);

            // 2️⃣ Only process if the category is selected (true)
            if (Boolean.TRUE.equals(categoryValue)) {
                List<String> selectedSubItems = new ArrayList<>();

                // 3️⃣ Iterate through the sub-items (e.g., ROM, Strength, Endurance, Stability)
                for (String subItemDisplayName : entry.getValue()) {
                    String subItemFieldName = fieldMappingService.lookupKeyByParentAndChild(SECTION, PROCEDURES,categoryDisplayName, subItemDisplayName)
                            .orElseThrow(() -> new IllegalArgumentException("Field not found: " + subItemDisplayName));

                    Object subItemValue = ProcedureMapper.getFieldValue(proceduresPlanOfCare, subItemFieldName);

                    // 4️⃣ If the sub-item is true, add it to the list
                    if (Boolean.TRUE.equals(subItemValue)) {
                        selectedSubItems.add(subItemDisplayName);
                    }
                    if(subItemValue instanceof String){
                        selectedSubItems.add((String) "Comment:"+subItemValue);
                    }
                }

                // 5️⃣ Put into result map only if sub-items exist
                if (!selectedSubItems.isEmpty()) {
                    selectedProcedures.put(categoryDisplayName, selectedSubItems);
                }
            }
        }

        return selectedProcedures;
    }
}
