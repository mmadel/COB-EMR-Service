package com.cob.emr.usecases.medical.note.finder;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.entity.medical.note.types.DailyNote;
import com.cob.emr.enums.MedicalNoteStatus;
import com.cob.emr.enums.MedicalNoteType;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FindRecentFinalizeDailyNoteUseCase {
    @Autowired
    MedicalNoteRepository medicalNoteRepository;
    private static final MedicalNoteType DAILY_MEDICAL_NOTE = MedicalNoteType.DAILY_NOTE;
    private static final MedicalNoteType INITIAL_MEDICAL_NOTE = MedicalNoteType.INITIAL_EVALUATION;
    private static final MedicalNoteStatus FINALIZE_MEDICAL_NOTE = MedicalNoteStatus.Finalized;

    public DailyNote find(Long caseId) {
        Optional<MedicalNote> optionalDaily = medicalNoteRepository.findTopByTypeAndStatusAndPatientCase_IdOrderByCreatedAtDesc(DAILY_MEDICAL_NOTE, FINALIZE_MEDICAL_NOTE, caseId);
        return (DailyNote)optionalDaily.get();
    }
}
