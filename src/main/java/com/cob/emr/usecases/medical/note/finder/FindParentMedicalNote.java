package com.cob.emr.usecases.medical.note.finder;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.enums.MedicalNoteStatus;
import com.cob.emr.enums.MedicalNoteType;
import com.cob.emr.exception.business.MedicalNoteException;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FindParentMedicalNote {
    @Autowired
    MedicalNoteRepository medicalNoteRepository;
    private static final MedicalNoteType INITIAL_MEDICAL_NOTE = MedicalNoteType.INITIAL_EVALUATION;
    private static final MedicalNoteType PROGRESS_MEDICAL_NOTE = MedicalNoteType.PROGRESS_NOTE;
    private static final MedicalNoteStatus FINALIZED_MEDICAL_NOTE = MedicalNoteStatus.Finalized;
    public MedicalNote find(Long caseId) throws MedicalNoteException {
        Optional<MedicalNote> optionalProgressNote =medicalNoteRepository.findTopByTypeAndStatusAndPatientCase_IdOrderByCreatedAtDesc(PROGRESS_MEDICAL_NOTE,FINALIZED_MEDICAL_NOTE,caseId);
        if(optionalProgressNote.isPresent())
            return optionalProgressNote.get();
        else{
            Optional<MedicalNote> optionalInitialNote = medicalNoteRepository.findTopByTypeAndStatusAndPatientCase_IdOrderByCreatedAtDesc(INITIAL_MEDICAL_NOTE,FINALIZED_MEDICAL_NOTE,caseId);
            if (optionalInitialNote.isPresent()) {
                return optionalInitialNote.get();
            }else{
                throw new MedicalNoteException(HttpStatus.CONFLICT, MedicalNoteException.FINALIZE_INIT_EXAM, new Object[]{});
            }
        }
    }
}
