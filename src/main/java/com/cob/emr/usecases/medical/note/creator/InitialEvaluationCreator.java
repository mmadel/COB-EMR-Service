package com.cob.emr.usecases.medical.note.creator;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.entity.medical.note.types.InitialEvaluation;
import com.cob.emr.enums.MedicalNoteType;
import com.cob.emr.model.medical.note.MedicalNoteRequest;
import com.cob.emr.repositories.patient.cases.PatientCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitialEvaluationCreator implements MedicalNoteCreator {
    @Autowired
    private PatientCaseRepository patientCaseRepository;

    @Override
    public MedicalNote createNote(MedicalNoteRequest medicalNoteRequest) {
        InitialEvaluation evaluation = new InitialEvaluation();
        evaluation.setPatientCase(patientCaseRepository.findById(medicalNoteRequest.getCaseId())
                .orElseThrow(() -> new IllegalArgumentException("Case not found")));
        evaluation.setType(MedicalNoteType.INITIAL_EVALUATION);
        evaluation.setCreatedBy(medicalNoteRequest.getCreatedBy());
        evaluation.setNoteDate(medicalNoteRequest.getNoteDate());
        evaluation.setSubjective(medicalNoteRequest.getSubjective());
        evaluation.setAssessment(medicalNoteRequest.getAssessment());
        evaluation.setPlanOfCare(medicalNoteRequest.getPlanOfCare());
        evaluation.setBilling(medicalNoteRequest.getBilling());
        return evaluation;
    }
}
