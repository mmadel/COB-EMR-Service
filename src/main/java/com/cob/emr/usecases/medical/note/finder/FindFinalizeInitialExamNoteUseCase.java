package com.cob.emr.usecases.medical.note.finder;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.entity.medical.note.types.InitialEvaluation;
import com.cob.emr.enums.MedicalNoteStatus;
import com.cob.emr.enums.MedicalNoteType;
import com.cob.emr.exception.business.MedicalNoteException;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FindFinalizeInitialExamNoteUseCase {
    @Autowired
    MedicalNoteRepository medicalNoteRepository;

    private static final MedicalNoteType MEDICAL_NOTE_TYPE = MedicalNoteType.INITIAL_EVALUATION;
    private static final MedicalNoteStatus MEDICAL_NOTE_STATUS = MedicalNoteStatus.Finalized;

    public MedicalNote find(Long caseId) throws MedicalNoteException {
        Optional<MedicalNote> optional = medicalNoteRepository.findByPatientCase_IdAndTypeAndStatus(caseId, MEDICAL_NOTE_TYPE, MEDICAL_NOTE_STATUS);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new MedicalNoteException(HttpStatus.CONFLICT, MedicalNoteException.FINALIZE_INIT_EXAM, new Object[]{});
    }
}
