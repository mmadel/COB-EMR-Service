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


    public void create(InitialEvaluation initialEvaluation) throws IOException {
        InitPlanOfCare initPlanOfCare = new InitPlanOfCare();
        if (initialEvaluation.getPlanOfCare().getProcedures() != null) {
            Map<String, List<String>> procedures = proceduresReader.read(initialEvaluation.getPlanOfCare().getProcedures());
            initPlanOfCare.setProcedures(procedures);
        }

        if (initialEvaluation.getPlanOfCare().getModalities() != null) {
            Map<String, List<String>> modalities = modalitiesReader.read(initialEvaluation.getPlanOfCare().getModalities());
            initPlanOfCare.setModalities(modalities);
        }

        if (initialEvaluation.getPlanOfCare().getSpecialties() != null) {
            Map<String, List<String>> specialties = specialtiesReader.read(initialEvaluation.getPlanOfCare().getSpecialties());
            initPlanOfCare.setSpecialties(specialties);
        }
        if (initialEvaluation.getPlanOfCare().getFrequency() != null)
            initPlanOfCare.setFrequency(FrequencyMapper.getFrequencyLabel(initialEvaluation.getPlanOfCare().getFrequency()).get());
        if (initialEvaluation.getPlanOfCare().getDuration() != null)
            initPlanOfCare.setDuration(DurationMapper.getDurationLabel(initialEvaluation.getPlanOfCare().getDuration()).get());
        if (initialEvaluation.getPlanOfCare().getPlan() != null)
            initPlanOfCare.setPlan(PlanMapper.getPlanLabel(initialEvaluation.getPlanOfCare().getPlan()).get());


        initPlanOfCare.setAssessment(createInitialExaminationAssessmentUseCase.create(initialEvaluation.getAssessment()));
        if (initPlanOfCare.getProcedures() != null
                || initPlanOfCare.getModalities() != null
                || initPlanOfCare.getSpecialties() != null
                || initPlanOfCare.getAssessment() != null)
            persistEntity(initPlanOfCare, initialEvaluation.getId());
    }

    private void persistEntity(InitPlanOfCare initPlanOfCar, Long initializeNoteId) {
        InitialExaminationPlanOfCareEntity entity = new InitialExaminationPlanOfCareEntity();
        entity.setInitPlanOfCareData(initPlanOfCar);
        entity.setInitialExaminationNoteId(initializeNoteId);
        initialExaminationPlanOfCareRepository.save(entity);
    }

}
