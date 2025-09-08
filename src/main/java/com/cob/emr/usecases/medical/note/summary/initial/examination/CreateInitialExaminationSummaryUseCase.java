package com.cob.emr.usecases.medical.note.summary.initial.examination;

import com.cob.emr.entity.medical.note.summary.InitialExaminationSummaryEntity;
import com.cob.emr.entity.medical.note.summary.patient.summary.PatientSummaryModel;
import com.cob.emr.entity.medical.note.types.InitialEvaluation;
import com.cob.emr.entity.patient.Patient;
import com.cob.emr.model.medical.note.summary.soap.SubjectiveSummary;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import com.cob.emr.repositories.medical.note.summary.InitialExaminationSummaryRepository;
import com.cob.emr.usecases.medical.note.summary.initial.examination.sction.subjective.CreateSubjectiveSummaryUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public void create(Long noteId) throws IOException {
        InitialEvaluation initialEvaluation = (InitialEvaluation) medicalNoteRepository.findById(noteId)
                .orElseThrow(() -> new IllegalArgumentException("Initial Evaluation Note Not Found"));
        SubjectiveSummary subjectiveSummary = createSubjectiveSummaryUseCase.create(initialEvaluation.getSubjective());
        initialEvaluation.getPatientCase().getPatient();
        persistEntity(subjectiveSummary,initialEvaluation);
        ;
    }

    private void persistEntity(SubjectiveSummary subjective,InitialEvaluation initialEvaluation){
        InitialExaminationSummaryEntity entity = new InitialExaminationSummaryEntity();
        entity.setNoteId(initialEvaluation.getId());
        entity.setSubjective(subjective);
        entity.setPatientData(getPatientSummary(initialEvaluation.getPatientCase().getPatient()));
        initialExaminationSummaryRepository.save(entity);
    }
    private PatientSummaryModel getPatientSummary(Patient patient) {
        PatientSummaryModel model = new PatientSummaryModel();
        model.setPatientName(patient.getLastName() +"," + patient.getFirstName());
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

