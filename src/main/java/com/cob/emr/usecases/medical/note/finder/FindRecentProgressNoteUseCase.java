package com.cob.emr.usecases.medical.note.finder;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.enums.MedicalNoteStatus;
import com.cob.emr.enums.MedicalNoteType;
import com.cob.emr.exception.business.MedicalNoteException;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class FindRecentProgressNoteUseCase {
    @Autowired
    MedicalNoteRepository medicalNoteRepository;
    private static final MedicalNoteType MEDICAL_NOTE_TYPE = MedicalNoteType.PROGRESS_NOTE;
    private static final MedicalNoteStatus MEDICAL_NOTE_STATUS = MedicalNoteStatus.Finalized;

    public MedicalNote find(Long caseId) throws MedicalNoteException {
        Optional<List<MedicalNote>> optionalList = medicalNoteRepository.findByPatientCase_IdAndType(caseId, MEDICAL_NOTE_TYPE);
        boolean isEmpty = optionalList.map(List::isEmpty).orElse(true);
        if (!isEmpty) {
            MedicalNote recentFinalized = getRecentIfFinalized(optionalList.get());
            if (recentFinalized != null)
                return recentFinalized;
            else
                throw new MedicalNoteException(HttpStatus.CONFLICT, MedicalNoteException.FINALIZE_PROGRESS_NOTE, new Object[]{});
        } else {
            return null;
        }
    }

    public MedicalNote getRecentIfFinalized(List<MedicalNote> notes) {
        return notes.stream()
                .max(Comparator.comparing(MedicalNote::getCreatedAt)) // get most recent
                .filter(note -> note.getStatus() == MEDICAL_NOTE_STATUS) // keep only if finalized
                .orElse(null); // return null if not finalized or empty
    }
}
