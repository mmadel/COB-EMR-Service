package com.cob.emr.usecases.medical.note.post.init.note.score;

import com.cob.emr.entity.medical.note.omt.score.InitialExaminationScoreEntity;
import com.cob.emr.entity.medical.note.types.InitialEvaluation;
import com.cob.emr.model.medical.note.finalize.init.exam.score.InitOMTScore;
import com.cob.emr.repositories.medical.note.InitialExaminationScoreRepository;
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
    ConvertToOMTScoresModelUseCase convertToOMTScoresModelUseCase;
    @Autowired
    MedicalNoteRepository medicalNoteRepository;
    @Autowired
    InitialExaminationScoreRepository initialExaminationScoreRepository;

    private final String SECTION = "objective";
    private final String CATEGORY = "omt";

    /*
        Steps
        1- Read OMTTest that will be got, it will return omtTestKeyName :
            readActiveOMTTestUseCase.read();
        2- Find The omtTestDisplayName
             fieldMappingService.lookupByDisplay(SECTION, CATEGORY, testName)
        3- Extract Value from Raw Data
            findValueFromRawDataUseCase.extractValues(objective,new ArrayList<>(testNameToOmtKey.values()))
        4- Convert to OMT Score Model
            convertToOMTScoresModelUseCase.convert(finalResult, omtTests)
     */
    public void create(Long initializeNoteId) throws IOException {
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


        Map<String, Object> rawResult = findValueFromRawDataUseCase.extractValues(objective,
                new ArrayList<>(testNameToOmtKey.values()));

        Map<String, Object> finalResult = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : testNameToOmtKey.entrySet()) {
            String testName = entry.getKey();
            String omtKey = entry.getValue();
            if (rawResult.containsKey(omtKey)) {
                finalResult.put(testName, rawResult.get(omtKey));
            }
        }
        List<InitOMTScore> initOmtScores = convertToOMTScoresModelUseCase.convert(finalResult, omtTests);
        persistEntity(initOmtScores, initializeNoteId);
    }

    private void persistEntity(List<InitOMTScore> initOmtScores, Long initializeNoteId) {
        InitialExaminationScoreEntity initialExaminationScoreEntity = new InitialExaminationScoreEntity();
        initialExaminationScoreEntity.setScoreData(initOmtScores);
        initialExaminationScoreEntity.setInitialExaminationNoteId(initializeNoteId);
        initialExaminationScoreRepository.save(initialExaminationScoreEntity);
    }
}

