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
public class FindRecentFinalizeDailyNoteUseCase {
    @Autowired
    MedicalNoteRepository medicalNoteRepository;
    private static final MedicalNoteType DAILY_MEDICAL_NOTE = MedicalNoteType.DAILY_NOTE;
    private static final MedicalNoteType INITIAL_MEDICAL_NOTE = MedicalNoteType.INITIAL_EVALUATION;
    private static final MedicalNoteStatus FINALIZE_MEDICAL_NOTE = MedicalNoteStatus.Finalized;

    public MedicalNote find(Long caseId) throws MedicalNoteException {
        Optional<MedicalNote> optionalDaily = medicalNoteRepository.findTopByTypeAndStatusAndPatientCase_IdOrderByCreatedByDesc(DAILY_MEDICAL_NOTE,FINALIZE_MEDICAL_NOTE,caseId);
        if (optionalDaily.isPresent())
            return optionalDaily.get();
        else{
            Optional<MedicalNote> optionalInitial = medicalNoteRepository.findTopByTypeAndStatusAndPatientCase_IdOrderByCreatedByDesc(INITIAL_MEDICAL_NOTE,FINALIZE_MEDICAL_NOTE,caseId);
            if(optionalInitial.isPresent())
                return optionalInitial.get();
            else
                throw new MedicalNoteException(HttpStatus.CONFLICT, MedicalNoteException.FINALIZE_INIT_EXAM, new Object[]{});
        }
    }
}
