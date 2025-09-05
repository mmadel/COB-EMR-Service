package com.cob.emr.usecases.medical.note.post.init.note.plan.reader;

import com.cob.emr.model.medical.note.types.initial.evaluation.plan.Modalities;
import com.cob.emr.model.medical.note.types.initial.evaluation.plan.Specialties;
import com.cob.emr.service.mapper.FieldMappingService;
import com.cob.emr.usecases.medical.note.post.init.note.plan.ReadPlanCategorisesUseCase;
import com.cob.emr.usecases.medical.note.post.init.note.plan.mappers.ModalitiesMapper;
import com.cob.emr.usecases.medical.note.post.init.note.plan.mappers.SpecialtiesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpecialtiesReader {

    private static final String SPECIALTIES = "Specialties";
    private final String SECTION = "plan";

    @Autowired
    ReadPlanCategorisesUseCase readPlanCategorisesUseCase;
    @Autowired
    FieldMappingService fieldMappingService;
    public Map<String, List<String>> read(Specialties specialtiesPlanOfCare) throws IOException {
        Map<String, List<String>> yamlSpecialties = readPlanCategorisesUseCase.get(SPECIALTIES);
        Map<String, List<String>> selectedSpecialties = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : yamlSpecialties.entrySet()) {
            String categoryDisplayName = entry.getKey();

            // 1️⃣ Get the key (field name in Procedures object) for the category
            String categoryFieldName = fieldMappingService.lookupKeyByDisplay(SECTION, SPECIALTIES, categoryDisplayName)
                    .orElseThrow(() -> new IllegalArgumentException("Field not found: " + categoryDisplayName));

            Object categoryValue = SpecialtiesMapper.getFieldValue(specialtiesPlanOfCare, categoryFieldName);

            // 2️⃣ Only process if the category is selected (true)
            if (Boolean.TRUE.equals(categoryValue)) {
                List<String> selectedSubItems = new ArrayList<>();

                // 3️⃣ Iterate through the sub-items (e.g., ROM, Strength, Endurance, Stability)
                for (String subItemDisplayName : entry.getValue()) {
                    String subItemFieldName = fieldMappingService.lookupKeyByParentAndChild(SECTION, SPECIALTIES,categoryDisplayName, subItemDisplayName)
                            .orElseThrow(() -> new IllegalArgumentException("Field not found: " + subItemDisplayName));

                    Object subItemValue = SpecialtiesMapper.getFieldValue(specialtiesPlanOfCare, subItemFieldName);

                    // 4️⃣ If the sub-item is true, add it to the list
                    if (Boolean.TRUE.equals(subItemValue)) {
                        selectedSubItems.add(subItemDisplayName);
                    }
                }

                // 5️⃣ Put into result map only if sub-items exist
                if (!selectedSubItems.isEmpty()) {
                    selectedSpecialties.put(categoryDisplayName, selectedSubItems);
                }
            }
        }

        return selectedSpecialties;
    }
}
