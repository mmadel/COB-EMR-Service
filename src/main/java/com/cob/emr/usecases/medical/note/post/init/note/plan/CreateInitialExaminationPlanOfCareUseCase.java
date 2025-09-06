package com.cob.emr.usecases.medical.note.post.init.note.plan;

import com.cob.emr.entity.medical.note.finalize.init.exam.plan.InitialExaminationPlanOfCareEntity;
import com.cob.emr.entity.medical.note.types.InitialEvaluation;
import com.cob.emr.model.medical.note.finalize.init.exam.plan.InitPlanOfCare;
import com.cob.emr.repositories.medical.note.InitialExaminationPlanOfCareRepository;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import com.cob.emr.usecases.medical.note.post.init.note.plan.mappers.header.DurationMapper;
import com.cob.emr.usecases.medical.note.post.init.note.plan.mappers.header.FrequencyMapper;
import com.cob.emr.usecases.medical.note.post.init.note.plan.mappers.header.PlanMapper;
import com.cob.emr.usecases.medical.note.post.init.note.plan.reader.ModalitiesReader;
import com.cob.emr.usecases.medical.note.post.init.note.plan.reader.ProceduresReader;
import com.cob.emr.usecases.medical.note.post.init.note.plan.reader.SpecialtiesReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
    @Autowired
    InitialExaminationPlanOfCareRepository initialExaminationPlanOfCareRepository;
    @Autowired
    CreateInitialExaminationAssessmentUseCase createInitialExaminationAssessmentUseCase;

    public void create(Long initializeNoteId) throws IOException {
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
        initPlanOfCare.setAssessment(createInitialExaminationAssessmentUseCase.create(initialEvaluation.getAssessment()));
        persistEntity(initPlanOfCare, initializeNoteId);
    }

    private void persistEntity(InitPlanOfCare initPlanOfCar, Long initializeNoteId) {
        InitialExaminationPlanOfCareEntity entity = new InitialExaminationPlanOfCareEntity();
        entity.setInitPlanOfCareData(initPlanOfCar);
        entity.setInitialExaminationNoteId(initializeNoteId);
        initialExaminationPlanOfCareRepository.save(entity);
    }

}
