package com.cob.emr.usecases.medical.note.post.init.note.plan;

import com.cob.emr.entity.medical.note.types.InitialEvaluation;
import com.cob.emr.model.medical.note.finalize.init.exam.plan.InitPlanOfCare;
import com.cob.emr.model.medical.note.types.initial.evaluation.plan.Procedures;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import com.cob.emr.service.mapper.FieldMappingService;
import com.cob.emr.usecases.medical.note.post.init.note.plan.mappers.ProcedureMapper;
import com.cob.emr.usecases.medical.note.post.init.note.plan.mappers.header.DurationMapper;
import com.cob.emr.usecases.medical.note.post.init.note.plan.mappers.header.FrequencyMapper;
import com.cob.emr.usecases.medical.note.post.init.note.plan.mappers.header.PlanMapper;
import com.cob.emr.usecases.medical.note.post.init.note.plan.reader.ModalitiesReader;
import com.cob.emr.usecases.medical.note.post.init.note.plan.reader.ProceduresReader;
import com.cob.emr.usecases.medical.note.post.init.note.plan.reader.SpecialtiesReader;
import com.cob.emr.usecases.medical.note.post.init.note.score.FindValueFromRawDataUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class CreateInitialExaminationPlanOfCareUseCase {
    @Autowired
    private MedicalNoteRepository medicalNoteRepository;
    @Autowired
    private ReadPlanCategorisesUseCase readPlanCategorisesUseCase;
    @Autowired
    private ProceduresReader proceduresReader;
    @Autowired
    ModalitiesReader modalitiesReader;
    @Autowired
    SpecialtiesReader specialtiesReader;

    public InitPlanOfCare create(Long initializeNoteId) throws IOException {
        InitPlanOfCare initPlanOfCare = new InitPlanOfCare();
        InitialEvaluation initialEvaluation = (InitialEvaluation) medicalNoteRepository.findById(initializeNoteId)
                .orElseThrow(() -> new IllegalArgumentException("Initial Evaluation Note Not Found"));
        Map<String, List<String>> procedures = proceduresReader.read(initialEvaluation.getPlanOfCare().getProcedures());
        Map<String, List<String>> modalities = modalitiesReader.read(initialEvaluation.getPlanOfCare().getModalities());
        Map<String, List<String>> specialties = specialtiesReader.read(initialEvaluation.getPlanOfCare().getSpecialties());

        initPlanOfCare.setFrequency(FrequencyMapper.getFrequencyLabel(initialEvaluation.getPlanOfCare().getFrequency()).get());
        initPlanOfCare.setDuration(DurationMapper.getDurationLabel(initialEvaluation.getPlanOfCare().getDuration()).get());
        initPlanOfCare.setPlan(PlanMapper.getPlanLabel(initialEvaluation.getPlanOfCare().getPlan()).get());
        initPlanOfCare.setProcedures(procedures);
        initPlanOfCare.setModalities(modalities);
        initPlanOfCare.setSpecialties(specialties);
        return initPlanOfCare;
    }


}
