package com.cob.emr.usecases.medical.note;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ForwardMedicalNotUseCase {
    @Autowired
    MedicalNoteRepository medicalNoteRepository;
    public void forward(Long medicalNoteId, String uuid){
        MedicalNote medicalNote = medicalNoteRepository.findById(medicalNoteId)
                .orElseThrow(() -> new IllegalArgumentException("medical note note found"));
        medicalNote.setFinalizedBy(uuid);
        medicalNoteRepository.save(medicalNote);
    }
}
