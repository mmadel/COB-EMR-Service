package com.cob.emr.usecases.medical.note.summary.initial.examination;

import com.cob.emr.entity.medical.note.summary.InitialExaminationSummaryEntity;
import com.cob.emr.entity.medical.note.summary.patient.summary.PatientSummaryModel;
import com.cob.emr.entity.medical.note.types.InitialEvaluation;
import com.cob.emr.entity.patient.Patient;
import com.cob.emr.model.medical.note.finalize.init.exam.plan.InitAssessment;
import com.cob.emr.model.medical.note.finalize.init.exam.plan.InitPlanOfCare;
import com.cob.emr.model.medical.note.summary.soap.ObjectiveSummary;
import com.cob.emr.model.medical.note.summary.soap.PlanOfCareSummary;
import com.cob.emr.model.medical.note.summary.soap.SubjectiveSummary;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import com.cob.emr.repositories.medical.note.summary.InitialExaminationSummaryRepository;
import com.cob.emr.usecases.medical.note.post.init.note.plan.CreateInitialExaminationPlanOfCareUseCase;
import com.cob.emr.usecases.medical.note.summary.section.assessment.CreateAssessmentSummaryUseCase;
import com.cob.emr.usecases.medical.note.summary.section.objective.CreateObjectiveSummaryUseCase;
import com.cob.emr.usecases.medical.note.summary.section.plan.CreatePlanOfCareSummaryUseCase;
import com.cob.emr.usecases.medical.note.summary.section.subjective.CreateSubjectiveSummaryUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class CreateInitialExaminationSummaryUseCase {
    @Autowired
    CreateSubjectiveSummaryUseCase createSubjectiveSummaryUseCase;
    @Autowired
    MedicalNoteRepository medicalNoteRepository;
    @Autowired
    InitialExaminationSummaryRepository initialExaminationSummaryRepository;
    @Autowired
    CreateObjectiveSummaryUseCase createObjectiveSummaryUseCase;
    @Autowired
    CreatePlanOfCareSummaryUseCase createPlanOfCareSummaryUseCase;
    @Autowired
    CreateAssessmentSummaryUseCase createAssessmentSummaryUseCase;

    @Transactional
    public void create(InitialEvaluation initialEvaluation , Long scoreId) throws IOException {
        SubjectiveSummary subjectiveSummary = createSubjectiveSummaryUseCase.create(initialEvaluation.getSubjective());
        ObjectiveSummary objectiveSummary = createObjectiveSummaryUseCase.create(initialEvaluation,scoreId);
        PlanOfCareSummary planOfCareSummary = createPlanOfCareSummaryUseCase.create(initialEvaluation);
        InitAssessment assessmentSummary = createAssessmentSummaryUseCase.create(initialEvaluation);
        initialEvaluation.getPatientCase().getPatient();
        persistEntity(subjectiveSummary, objectiveSummary, planOfCareSummary, assessmentSummary, initialEvaluation);
    }

    private void persistEntity(SubjectiveSummary subjective,
                               ObjectiveSummary objectiveSummary,
                               PlanOfCareSummary planOfCareSummary,
                               InitAssessment assessmentSummary, InitialEvaluation initialEvaluation) {
        InitialExaminationSummaryEntity entity = new InitialExaminationSummaryEntity();
        entity.setNoteId(initialEvaluation.getId());
        entity.setSubjective(subjective);
        entity.setObjective(objectiveSummary);
        entity.setPlanOfCare(planOfCareSummary);
        entity.setAssessmentSummary(assessmentSummary);
        entity.setPatientData(getPatientSummary(initialEvaluation.getPatientCase().getPatient()));
        initialExaminationSummaryRepository.save(entity);
    }

    private PatientSummaryModel getPatientSummary(Patient patient) {
        PatientSummaryModel model = new PatientSummaryModel();
        model.setPatientName(patient.getLastName() + "," + patient.getFirstName());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
                .withZone(ZoneId.systemDefault());
        String formattedDate = formatter.format(Instant.ofEpochMilli(patient.getBirthDate()));
        model.setDob(formattedDate);
        model.setInsuranceName(patient.getPatientInsuranceModels()
                .stream().findFirst().get().getInsuranceCompany().getName());
        /*todo
            set Patient's Provider
         */
        model.setProviderNumber("dummy");
        return model;
    }
}

