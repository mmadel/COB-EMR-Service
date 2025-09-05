package com.cob.emr.usecases.medical.note.post.init.note.plan;

import com.cob.emr.entity.medical.note.types.InitialEvaluation;
import com.cob.emr.model.medical.note.finalize.init.exam.plan.InitPlanOfCare;
import com.cob.emr.model.medical.note.types.initial.evaluation.plan.Procedures;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import com.cob.emr.service.mapper.FieldMappingService;
import com.cob.emr.usecases.medical.note.post.init.note.plan.mappers.ProcedureMapper;
import com.cob.emr.usecases.medical.note.post.init.note.score.FindValueFromRawDataUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class CreateInitialExaminationPlanOfCareUseCase {
    @Autowired
    MedicalNoteRepository medicalNoteRepository;
    @Autowired
    ReadPlanCategorisesUseCase readPlanCategorisesUseCase;
    @Autowired
    FieldMappingService fieldMappingService;
    @Autowired
    FindValueFromRawDataUseCase findValueFromRawDataUseCase;

    private static final String PROCEDURES = "Procedures";
    private static final String MODALITIES = "Modalities";
    private static final String SPECIALTIES = "Specialties";
    private final String SECTION = "plan";

    public InitPlanOfCare create(Long initializeNoteId) throws IOException {
        InitPlanOfCare initPlanOfCare = new InitPlanOfCare();
        InitialEvaluation initialEvaluation = (InitialEvaluation) medicalNoteRepository.findById(initializeNoteId)
                .orElseThrow(() -> new IllegalArgumentException("Initial Evaluation Note Not Found"));
        initPlanOfCare.setProcedures(readProcedures(initialEvaluation.getPlanOfCare().getProcedures()));

        //        Map<String, List<String>>  modalities= readPlanCategorisesUseCase.get(MODALITIES);
//        Map<String, List<String>>  specialties= readPlanCategorisesUseCase.get(SPECIALTIES);
//        Object objective = initialEvaluation.getPlanOfCare();
        return initPlanOfCare;
    }

    private Map<String, List<String>> readProcedures(Procedures proceduresPlanOfCare) throws IOException {
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
