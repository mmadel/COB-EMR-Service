package com.cob.emr.usecases.medical.note;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.entity.medical.note.types.InitialEvaluation;
import com.cob.emr.model.medical.note.MedicalNoteRequest;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DraftMedicalNoteUseCase {
    @Autowired
    MedicalNoteRepository medicalNoteRepository;

    public void draft(MedicalNoteRequest request) {
        MedicalNote medicalNote = medicalNoteRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalArgumentException("Medical Note Not found"));
        if (medicalNote instanceof InitialEvaluation)
            draftInitialEvaluation(medicalNote, request);
    }

    private void draftInitialEvaluation(MedicalNote medicalNote, MedicalNoteRequest request) {
        InitialEvaluation initialEvaluation = (InitialEvaluation) medicalNote;
        if (request.getSubjective() != null)
            initialEvaluation.setSubjective(request.getSubjective());
        if (request.getAssessment() != null)
            initialEvaluation.setAssessment(request.getAssessment());
        if (request.getPlanOfCare() != null)
            initialEvaluation.setPlanOfCare(request.getPlanOfCare());
        if (request.getBilling() != null)
            initialEvaluation.setBilling(request.getBilling());
        if(request.getObjective() != null)
            initialEvaluation.setObjective(request.getObjective());
        medicalNoteRepository.save(initialEvaluation);
    }
}
