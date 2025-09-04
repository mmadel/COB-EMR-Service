package com.cob.emr.usecases.medical.note.post.init.note;

import com.cob.emr.entity.medical.note.types.InitialEvaluation;
import com.cob.emr.model.medical.note.omt.test.score.OMTScore;
import com.cob.emr.model.medical.note.omt.test.score.ScoreValue;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import com.cob.emr.service.mapper.FieldMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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

    public List<OMTScore> create(Long initializeNoteId) throws IOException {
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
        return convertToOMTScores(finalResult, omtTests);

    }

    public List<OMTScore> convertToOMTScores(Map<String, Object> rawResult) {
        List<OMTScore> omtScores = new ArrayList<>();

        for (Map.Entry<String, Object> entry : rawResult.entrySet()) {
            String examName = entry.getKey();
            Object valuesObj = entry.getValue();

            if (valuesObj instanceof Map) {
                Map<String, Object> values = (Map<String, Object>) valuesObj;

                List<ScoreValue> scoreValues = values.entrySet().stream()
                        .map(e -> {
                            String key = e.getKey();
                            // Normalize "empty" style keys
                            if (key == null || key.trim().isEmpty()
                                    || key.equalsIgnoreCase("empty")
                                    || key.equalsIgnoreCase("empty_test")) {
                                key = "score";
                            }
                            return new ScoreValue(
                                    key,                                 // scoreName
                                    e.getValue() instanceof Number       // scoreValue
                                            ? ((Number) e.getValue()).doubleValue()
                                            : null
                            );
                        })
                        .collect(Collectors.toList());

                OMTScore omtScore = new OMTScore();
                omtScore.setExamName(examName);
                omtScore.setScoreValue(scoreValues);

                omtScores.add(omtScore);
            }
        }
        return omtScores;
    }

    public List<OMTScore> convertToOMTScores(
            Map<String, Object> rawResult,
            Map<String, List<String>> omtTests) {

        List<OMTScore> omtScores = new ArrayList<>();

        for (Map.Entry<String, Object> entry : rawResult.entrySet()) {
            String examName = entry.getKey();
            Object valuesObj = entry.getValue();

            if (valuesObj instanceof Map) {
                Map<String, Object> values = (Map<String, Object>) valuesObj;

                // expected labels from omtTests
                List<String> expectedLabels = omtTests.getOrDefault(examName, Collections.emptyList());

                List<ScoreValue> scoreValues = new ArrayList<>();

                for (Map.Entry<String, Object> valEntry : values.entrySet()) {
                    String rawKey = valEntry.getKey();
                    Object rawValue = valEntry.getValue();

                    // try to match the suffix with an expected label
                        String simplifiedName = expectedLabels.stream()
                            .filter(label -> rawKey.toLowerCase().endsWith(label.toLowerCase()))
                            .findFirst()
                            .orElse(rawKey); // fallback: keep rawKey if no match
                    if (simplifiedName == null || simplifiedName.trim().isEmpty()
                            || simplifiedName.equalsIgnoreCase("empty")
                            || simplifiedName.equalsIgnoreCase("empty_test")) {
                        simplifiedName = "score";
                    }
                    scoreValues.add(new ScoreValue(
                            simplifiedName,
                            rawValue instanceof Number ? ((Number) rawValue).doubleValue() : null
                    ));
                }

                OMTScore omtScore = new OMTScore();
                omtScore.setExamName(examName);
                omtScore.setScoreValue(scoreValues);

                omtScores.add(omtScore);
            }
        }

        return omtScores;
    }

}

