package com.cob.emr.usecases.medical.note;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.enums.MedicalNoteStatus;
import com.cob.emr.enums.MedicalNoteType;
import com.cob.emr.exception.business.MedicalNoteException;
import com.cob.emr.model.medical.note.FinalizeMedicalNoteRequest;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import com.cob.emr.usecases.medical.note.post.init.note.PostFinalizeInitialNoteUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
public class FinalizeMedialNoteUseCase {

    @Autowired
    private MedicalNoteRepository medicalNoteRepository;
    @Autowired
    PostFinalizeInitialNoteUseCase postFinalizeInitialNoteUseCase;
    @Transactional
    public void finalize(FinalizeMedicalNoteRequest request) throws MedicalNoteException, IOException {
        MedicalNote medicalNote = medicalNoteRepository.findByPatientCase_IdAndId(request.getCaseId(), request.getId())
                .orElseThrow(() -> new IllegalArgumentException("Medical Note Not Found"));
        medicalNote.setFinalizedBy(request.getFinalizedBy());
        medicalNote.setStatus(MedicalNoteStatus.Finalized);
        medicalNoteRepository.save(medicalNote);
        if(medicalNote.getType().equals(MedicalNoteType.INITIAL_EVALUATION))
            postFinalizeInitialNoteUseCase.execute(request.getCaseId(),medicalNote.getId());

    }
}
