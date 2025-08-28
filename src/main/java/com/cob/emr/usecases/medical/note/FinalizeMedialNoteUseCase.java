package com.cob.emr.usecases.medical.note;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.enums.MedicalNoteStatus;
import com.cob.emr.model.medical.note.FinalizeMedicalNoteRequest;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class FinalizeMedialNoteUseCase {

    @Autowired
    MedicalNoteRepository medicalNoteRepository;
    @Autowired
    SaveMedicalNoteUseCase saveMedicalNoteUseCase;
    @Transactional
    public void finalize(FinalizeMedicalNoteRequest request) {
        MedicalNote medicalNote = medicalNoteRepository.findByPatientCase_IdAndId(request.getCaseId(), request.getId())
                .orElseThrow(() -> new IllegalArgumentException("Medical Note Not Found"));
        medicalNote.setFinalizedBy(request.getFinalizedBy());
        medicalNote.setStatus(MedicalNoteStatus.Finalized);
        saveMedicalNoteUseCase.draft(request.getMedicalNoteRequest());
        medicalNoteRepository.save(medicalNote);

    }
}
