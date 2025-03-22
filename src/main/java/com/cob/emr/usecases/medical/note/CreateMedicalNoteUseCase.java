package com.cob.emr.usecases.medical.note;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.enums.MedicalNoteStatus;
import com.cob.emr.model.medical.note.MedicalNoteRequest;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import com.cob.emr.usecases.medical.note.creator.MedicalNoteCreator;
import com.cob.emr.usecases.medical.note.creator.MedicalNoteFactoryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateMedicalNoteUseCase {
    @Autowired
    MedicalNoteFactoryProvider medicalNoteProvider;
    @Autowired
    MedicalNoteRepository medicalNoteRepository;

    public MedicalNote createMedicalNote(MedicalNoteRequest request) {
        MedicalNoteCreator creator = medicalNoteProvider.getFactory(request.getNoteType());
        MedicalNote note = creator.createNote(request);
        note.setStatus(MedicalNoteStatus.Create);
        return medicalNoteRepository.save(note);
    }
}
