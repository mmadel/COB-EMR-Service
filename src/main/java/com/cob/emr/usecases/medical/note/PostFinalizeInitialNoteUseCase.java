package com.cob.emr.usecases.medical.note;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.enums.MedicalNoteStatus;
import com.cob.emr.exception.business.MedicalNoteException;
import com.cob.emr.model.medical.note.MedicalNoteRequest;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import com.cob.emr.usecases.medical.note.creator.DailyNoteCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostFinalizeInitialNoteUseCase {
    @Autowired
    private DailyNoteCreator dailyNoteCreator;
    @Autowired
    MedicalNoteRepository medicalNoteRepository;

    public void execute(Long caseId) throws MedicalNoteException {
        MedicalNoteRequest medicalNoteRequest = new MedicalNoteRequest();
        medicalNoteRequest.setCaseId(caseId);
        MedicalNote note = dailyNoteCreator.createNote(medicalNoteRequest);
        note.setStatus(MedicalNoteStatus.Finalized);
        medicalNoteRepository.save(note);

    }
}
