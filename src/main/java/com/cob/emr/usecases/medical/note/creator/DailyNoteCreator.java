package com.cob.emr.usecases.medical.note.creator;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.entity.medical.note.types.DailyNote;
import com.cob.emr.entity.medical.note.types.InitialEvaluation;
import com.cob.emr.enums.MedicalNoteType;
import com.cob.emr.exception.business.MedicalNoteException;
import com.cob.emr.model.medical.note.MedicalNoteRequest;
import com.cob.emr.usecases.medical.note.finder.FindFinalizeInitialExamNoteUseCase;
import com.cob.emr.usecases.medical.note.finder.FindRecentFinalizeDailyNoteUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DailyNoteCreator implements MedicalNoteCreator {
    @Autowired
    FindFinalizeInitialExamNoteUseCase findFinalizeInitialExamNoteUseCase;
    @Autowired
    FindRecentFinalizeDailyNoteUseCase findRecentFinalizeDailyNoteUseCase;

    public MedicalNote createNote(MedicalNoteRequest medicalNoteRequest) throws MedicalNoteException {
        MedicalNote medicalNote = getProperMedicalNote(medicalNoteRequest.getCaseId());
        return create(medicalNote);
    }

    private MedicalNote getProperMedicalNote(Long caseId) throws MedicalNoteException {
        MedicalNote medicalNote = findRecentFinalizeDailyNoteUseCase.find(caseId);
        if (medicalNote != null)
            return medicalNote;
        else
            return findFinalizeInitialExamNoteUseCase.find(caseId);

    }

    private DailyNote create(MedicalNote medicalNote) {
        DailyNote dailyNote = new DailyNote();
        dailyNote.setPatientCase(medicalNote.getPatientCase());
        dailyNote.setType(MedicalNoteType.DAILY_NOTE);
        dailyNote.setCreatedBy(medicalNote.getCreatedBy());
        dailyNote.setNoteDate(medicalNote.getNoteDate());
        if (medicalNote instanceof DailyNote)
            createFromDaily(dailyNote, (DailyNote) medicalNote);
        if (medicalNote instanceof InitialEvaluation)
            createFromInitialEvaluation(dailyNote, (InitialEvaluation) medicalNote);
        return dailyNote;
    }

    private void createFromDaily(DailyNote target, DailyNote source) {
        target.setSubjective(source.getSubjective());
        target.setObjective(source.getObjective());
        target.setAssessment(source.getAssessment());
        target.setPlanOfCare(source.getPlanOfCare());
    }

    private void createFromInitialEvaluation(DailyNote target, InitialEvaluation source) {
        target.setSubjective(source.getSubjective());
        target.setObjective(source.getBilling());
        target.setAssessment(source.getAssessment());
        target.setPlanOfCare(source.getPlanOfCare());
    }


}
