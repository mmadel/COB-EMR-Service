package com.cob.emr.usecases.medical.note;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.enums.MedicalNoteStatus;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class RemoveMedicalNoteUseCase {
    @Autowired
    MedicalNoteRepository medicalNoteRepository;

    @Transactional
    public void remove(Long Id) {
        MedicalNote medicalNote = medicalNoteRepository.findById(Id)
                .orElseThrow(() -> new IllegalArgumentException("Medical Note is not found"));
        medicalNote.setStatus(MedicalNoteStatus.Remove);
        medicalNoteRepository.save(medicalNote);
    }
}
