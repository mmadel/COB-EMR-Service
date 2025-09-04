package com.cob.emr.usecases.medical.note.post.init.note;

import com.cob.emr.entity.medical.note.types.InitialEvaluation;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import com.cob.emr.service.mapper.FieldMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

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

        Map<String, String> testNameToOmtKey = new HashMap<>();
        Map<String, List<String>> omtTests = readActiveOMTTestUseCase.read();

        for (Map.Entry<String, List<String>> entry : omtTests.entrySet()) {
            String testName = entry.getKey();
            List<String> list = fieldMappingService.lookupByDisplay(SECTION, CATEGORY, testName)
                    .orElseThrow(() -> new IllegalArgumentException("Field not found: " + testName));
            String omtKeyName = list.stream().findFirst().get();

            testNameToOmtKey.put(testName, omtKeyName);
        }

        // Extract values using omtKeyNames
        Map<String, Object> rawResult = findValueFromRawDataUseCase.extractValues(objective,
                new ArrayList<>(testNameToOmtKey.values()));

        // Transform the result map so keys are human-readable test names
        Map<String, Object> finalResult = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : testNameToOmtKey.entrySet()) {
            String testName = entry.getKey();
            String omtKey = entry.getValue();
            if (rawResult.containsKey(omtKey)) {
                finalResult.put(testName, rawResult.get(omtKey));
            }
        }

        return finalResult;
    }
}

