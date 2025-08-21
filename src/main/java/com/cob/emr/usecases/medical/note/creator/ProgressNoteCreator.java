package com.cob.emr.usecases.medical.note.creator;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.entity.medical.note.types.DailyNote;
import com.cob.emr.entity.medical.note.types.InitialEvaluation;
import com.cob.emr.entity.medical.note.types.ProgressNote;
import com.cob.emr.enums.MedicalNoteStatus;
import com.cob.emr.enums.MedicalNoteType;
import com.cob.emr.exception.business.MedicalNoteException;
import com.cob.emr.model.medical.note.MedicalNoteRequest;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import com.cob.emr.repositories.patient.cases.PatientCaseRepository;
import com.cob.emr.usecases.medical.note.finder.FindFinalizeInitialExamNoteUseCase;
import com.cob.emr.usecases.medical.note.finder.FindRecentProgressNoteUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProgressNoteCreator implements MedicalNoteCreator {
    @Autowired
    MedicalNoteRepository medicalNoteRepository;
    @Autowired
    PatientCaseRepository patientCaseRepository;
    @Autowired
    FindRecentProgressNoteUseCase findRecentProgressNoteUseCase;
    @Autowired
    FindFinalizeInitialExamNoteUseCase findFinalizeInitialExamNoteUseCase;

    @Override
    public MedicalNote createNote(MedicalNoteRequest medicalNoteRequest) throws MedicalNoteException {
        MedicalNote medicalNote = getProperMedicalNote(medicalNoteRequest.getCaseId());
        return create(medicalNote);
    }

    private MedicalNote getProperMedicalNote(Long caseId) throws MedicalNoteException {
        MedicalNote medicalNote = findRecentProgressNoteUseCase.find(caseId);
        if (medicalNote != null)
            return medicalNote;
        else
            return findFinalizeInitialExamNoteUseCase.find(caseId);
    }

    private ProgressNote create(MedicalNote medicalNote) {
        ProgressNote progressNote = new ProgressNote();
        progressNote.setPatientCase(medicalNote.getPatientCase());
        progressNote.setType(MedicalNoteType.PROGRESS_NOTE);
        progressNote.setCreatedBy(medicalNote.getCreatedBy());
        progressNote.setNoteDate(medicalNote.getNoteDate());
        if (medicalNote instanceof DailyNote)
            createFromProgress(progressNote, (ProgressNote) medicalNote);
        if (medicalNote instanceof InitialEvaluation)
            createFromInitialEvaluation(progressNote, (InitialEvaluation) medicalNote);
        return progressNote;
    }

    private void createFromProgress(ProgressNote target, ProgressNote source) {
        target.setSubjective(source.getSubjective());
        target.setObjective(source.getBilling());
        target.setAssessment(source.getAssessment());
        target.setPlanOfCare(source.getPlanOfCare());
    }

    private void createFromInitialEvaluation(ProgressNote target, InitialEvaluation source) {
        target.setSubjective(source.getSubjective());
        target.setObjective(source.getBilling());
        target.setAssessment(source.getAssessment());
        target.setPlanOfCare(source.getPlanOfCare());
    }
}
