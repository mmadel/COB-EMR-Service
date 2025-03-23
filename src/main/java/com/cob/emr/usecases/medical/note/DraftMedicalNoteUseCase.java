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
        initialEvaluation.setSubjective(request.getSubjective());
        initialEvaluation.setAssessment(request.getAssessment());
        initialEvaluation.setPlanOfCare(request.getPlanOfCare());
        initialEvaluation.setBilling(request.getBilling());
        medicalNoteRepository.save(initialEvaluation);
    }
}
