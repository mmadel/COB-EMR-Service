package com.cob.emr.usecases.medical.note.finder;

import com.cob.emr.entity.medical.note.MedicalNote;
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
    private static final MedicalNoteType MEDICAL_NOTE_TYPE = MedicalNoteType.DAILY_NOTE;
    private static final MedicalNoteStatus MEDICAL_NOTE_STATUS = MedicalNoteStatus.Finalized;

    public MedicalNote find(Long caseId) {
        Optional<MedicalNote> optional = medicalNoteRepository.findTopByTypeAndStatusOrderByCreatedAtDesc(MEDICAL_NOTE_TYPE, MEDICAL_NOTE_STATUS);
        if (optional.isPresent())
            return optional.get();
        else
            return null;
    }
}
