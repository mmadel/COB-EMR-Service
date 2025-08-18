package com.cob.emr.usecases.medical.note;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.entity.medical.note.types.*;
import com.cob.emr.enums.MedicalNoteStatus;
import com.cob.emr.exception.business.MedicalNoteException;
import com.cob.emr.exception.business.PatientException;
import com.cob.emr.model.medical.note.MedicalNoteRequest;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class SaveMedicalNoteUseCase {
    @Autowired
    MedicalNoteRepository medicalNoteRepository;

    @Transactional
    public MedicalNote draft(MedicalNoteRequest request) {
        MedicalNote medicalNote = medicalNoteRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalArgumentException("Medical Note Not found"));

        medicalNote.setStatus(MedicalNoteStatus.Draft);
        medicalNoteRepository.save(medicalNote);

        if (medicalNote instanceof InitialEvaluation)
            draftInitialEvaluation(medicalNote, request);
        if (medicalNote instanceof DailyNote)
            draftDailyNote(medicalNote, request);
        if (medicalNote instanceof ProgressNote)
            draftProgressNote(medicalNote, request);
        if (medicalNote instanceof QuickDischargeNote)
            draftQuickDischargeNote(medicalNote, request);
        if (medicalNote instanceof FullDischargeNote)
            draftFullDischargeNote(medicalNote, request);
        return medicalNote;
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
        if (request.getObjective() != null)
            initialEvaluation.setObjective(request.getObjective());
        medicalNoteRepository.save(initialEvaluation);
    }

    private void draftDailyNote(MedicalNote medicalNote, MedicalNoteRequest request) {
        DailyNote dailyNote = (DailyNote) medicalNote;
        if (request.getSubjective() != null)
            dailyNote.setSubjective(request.getSubjective());
        if (request.getAssessment() != null)
            dailyNote.setAssessment(request.getAssessment());
        if (request.getPlanOfCare() != null)
            dailyNote.setPlanOfCare(request.getPlanOfCare());
        if (request.getObjective() != null)
            dailyNote.setObjective(request.getObjective());
        medicalNoteRepository.save(dailyNote);
    }

    private void draftProgressNote(MedicalNote medicalNote, MedicalNoteRequest request) {
        ProgressNote progressNote = (ProgressNote) medicalNote;
        if (request.getSubjective() != null)
            progressNote.setSubjective(request.getSubjective());
        if (request.getAssessment() != null)
            progressNote.setAssessment(request.getAssessment());
        if (request.getPlanOfCare() != null)
            progressNote.setPlanOfCare(request.getPlanOfCare());
        if (request.getBilling() != null)
            progressNote.setBilling(request.getBilling());
        if (request.getObjective() != null)
            progressNote.setObjective(request.getObjective());
        medicalNoteRepository.save(progressNote);
    }

    private void draftQuickDischargeNote(MedicalNote medicalNote, MedicalNoteRequest request) {
        QuickDischargeNote quickDischargeNote = (QuickDischargeNote) medicalNote;
        quickDischargeNote.setDischargeDate(request.getQuickDischargeRequest().getDischargeDate());
        quickDischargeNote.setNumberOfVisits(request.getQuickDischargeRequest().getNumberOfVisits());
        quickDischargeNote.setComment(request.getQuickDischargeRequest().getComment());
        medicalNoteRepository.save(quickDischargeNote);
    }

    private void draftFullDischargeNote(MedicalNote medicalNote, MedicalNoteRequest request) {
        FullDischargeNote fullDischargeNote = (FullDischargeNote) medicalNote;
        if (request.getSubjective() != null)
            fullDischargeNote.setSubjective(request.getSubjective());
        if (request.getAssessment() != null)
            fullDischargeNote.setAssessment(request.getAssessment());
        if (request.getPlanOfCare() != null)
            fullDischargeNote.setPlanOfCare(request.getPlanOfCare());
        if (request.getBilling() != null)
            fullDischargeNote.setBilling(request.getBilling());
        if (request.getObjective() != null)
            fullDischargeNote.setObjective(request.getObjective());
        medicalNoteRepository.save(fullDischargeNote);
    }
}
