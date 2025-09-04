package com.cob.emr.usecases.medical.note.post.init.note;

import com.cob.emr.entity.medical.note.types.InitialEvaluation;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import com.cob.emr.service.mapper.FieldMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CreateInitialExaminationScoreUseCase {
    @Autowired
    ReadActiveOMTTestUseCase readActiveOMTTestUseCase;
    @Autowired
    FieldMappingService fieldMappingService;
    @Autowired
    FindValueFromRawDataUseCase findValueFromRawDataUseCase;

    @Autowired
    MedicalNoteRepository medicalNoteRepository;
    private final String SECTION = "objective";
    private final String CATEGORY = "omt";


    public Map<String, Object> create(Long initializeNoteId) throws IOException {
        InitialEvaluation initialEvaluation = (InitialEvaluation) medicalNoteRepository.findById(initializeNoteId)
                .orElseThrow(() -> new IllegalArgumentException("Initial Evaluation Note Not Found"));

        Object objective = initialEvaluation.getObjective();

        List<String> result = new ArrayList<>();
        Map<String, List<String>> omtTests = readActiveOMTTestUseCase.read();
        for (Map.Entry<String, List<String>> entry : omtTests.entrySet()) {
            String name = entry.getKey();
            List<String> score = entry.getValue();
            List<String> list = fieldMappingService.lookupByDisplay(SECTION, CATEGORY, name)
                    .orElseThrow(() -> new IllegalArgumentException("Field not found: " + name));
            String omtTest = list.stream().findFirst().get();
            result.add(omtTest);
        }
        return findValueFromRawDataUseCase.extractValues(objective, result);
    }
}
