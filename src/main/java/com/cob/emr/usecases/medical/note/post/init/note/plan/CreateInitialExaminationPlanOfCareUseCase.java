package com.cob.emr.usecases.medical.note.post.init.note.plan;

import com.cob.emr.entity.medical.note.types.InitialEvaluation;
import com.cob.emr.model.medical.note.finalize.init.exam.plan.InitPlanOfCare;
import com.cob.emr.model.medical.note.types.initial.evaluation.plan.Procedures;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import com.cob.emr.service.mapper.FieldMappingService;
import com.cob.emr.usecases.medical.note.post.init.note.plan.mappers.ProcedureMapper;
import com.cob.emr.usecases.medical.note.post.init.note.plan.reader.ProceduresReader;
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
    ProceduresReader proceduresReader;

    private static final String SPECIALTIES = "Specialties";
    private final String SECTION = "plan";

    public InitPlanOfCare create(Long initializeNoteId) throws IOException {
        InitPlanOfCare initPlanOfCare = new InitPlanOfCare();
        InitialEvaluation initialEvaluation = (InitialEvaluation) medicalNoteRepository.findById(initializeNoteId)
                .orElseThrow(() -> new IllegalArgumentException("Initial Evaluation Note Not Found"));
        Map<String, List<String>> procedures =proceduresReader.read(initialEvaluation.getPlanOfCare().getProcedures());
        initPlanOfCare.setProcedures(procedures);

        //        Map<String, List<String>>  modalities= readPlanCategorisesUseCase.get(MODALITIES);
//        Map<String, List<String>>  specialties= readPlanCategorisesUseCase.get(SPECIALTIES);
//        Object objective = initialEvaluation.getPlanOfCare();
        return initPlanOfCare;
    }



}
