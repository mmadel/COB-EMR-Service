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
import com.cob.emr.usecases.medical.note.finder.FindFinalizeInitialExamNoteUseCase;
import com.cob.emr.usecases.medical.note.finder.FindParentMedicalNote;
import com.cob.emr.usecases.medical.note.finder.FindRecentFinalizeDailyNoteUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DailyNoteCreator implements MedicalNoteCreator {
    @Autowired
    FindRecentFinalizeDailyNoteUseCase findRecentFinalizeDailyNoteUseCase;
    @Autowired
    FindFinalizeInitialExamNoteUseCase findFinalizeInitialExamNoteUseCase;
    @Autowired
    FindParentMedicalNote findParentMedicalNote;
    @Autowired
    MedicalNoteRepository medicalNoteRepository;

    public MedicalNote createNote(MedicalNoteRequest medicalNoteRequest) throws MedicalNoteException {
        MedicalNote medicalNote = find(medicalNoteRequest.getCaseId());
        return create(medicalNote);
    }

    private MedicalNote find(Long caseId) throws MedicalNoteException {
        MedicalNote parentMedicalNote = findParentMedicalNote.find(caseId);
        Optional<MedicalNote> optionalDaily = medicalNoteRepository.findTopByTypeAndStatusAndPatientCase_IdOrderByCreatedAtDesc
                (MedicalNoteType.DAILY_NOTE, MedicalNoteStatus.Finalized, caseId);
        if (optionalDaily.isPresent()) {
            MedicalNote dailyMedicalNote = optionalDaily.get();
            DailyNote dailyNote = (DailyNote) dailyMedicalNote;
            if (dailyNote.getParentNote().equals(parentMedicalNote.getId()))
                return dailyMedicalNote;
            else
                return parentMedicalNote;
        } else {
            return parentMedicalNote;
        }
    }

    private DailyNote create(MedicalNote medicalNote) {
        DailyNote dailyNote = new DailyNote();
        dailyNote.setPatientCase(medicalNote.getPatientCase());
        dailyNote.setType(MedicalNoteType.DAILY_NOTE);
        dailyNote.setCreatedBy(medicalNote.getCreatedBy());
        dailyNote.setNoteDate(medicalNote.getNoteDate());
        if (medicalNote instanceof DailyNote)
            createFromDailyNote(dailyNote, (DailyNote) medicalNote);
        else
            createParentNote(dailyNote, medicalNote);
        return dailyNote;
    }

    private void createFromDailyNote(DailyNote target, DailyNote source) {
        target.setSubjective(source.getSubjective());
        target.setObjective(source.getObjective());
        target.setAssessment(source.getAssessment());
        target.setPlanOfCare(source.getPlanOfCare());
        target.setParentNote(source.getParentNote());
    }

    private void createParentNote(DailyNote target, MedicalNote source) {
        if (source instanceof InitialEvaluation) {
            InitialEvaluation initialEvaluation = (InitialEvaluation) source;
            target.setSubjective(initialEvaluation.getSubjective());
            target.setObjective(initialEvaluation.getBilling());
            target.setAssessment(initialEvaluation.getAssessment());
            target.setPlanOfCare(initialEvaluation.getPlanOfCare());
        }
        if (source instanceof ProgressNote) {
            ProgressNote progressNote = (ProgressNote) source;
            target.setSubjective(progressNote.getSubjective());
            target.setObjective(progressNote.getBilling());
            target.setAssessment(progressNote.getAssessment());
            target.setPlanOfCare(progressNote.getPlanOfCare());
        }
        target.setParentNote(source.getId());
    }
}
