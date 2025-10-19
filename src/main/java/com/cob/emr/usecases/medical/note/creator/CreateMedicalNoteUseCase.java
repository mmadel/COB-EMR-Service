package com.cob.emr.usecases.medical.note.creator;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.enums.MedicalNoteStatus;
import com.cob.emr.exception.business.MedicalNoteException;
import com.cob.emr.model.medical.note.MedicalNoteRequest;
import com.cob.emr.model.medical.note.MedicalResponse;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateMedicalNoteUseCase {
    @Autowired
    MedicalNoteFactoryProvider medicalNoteProvider;
    @Autowired
    MedicalNoteRepository medicalNoteRepository;


    public MedicalResponse createMedicalNote(MedicalNoteRequest request) throws MedicalNoteException {
        MedicalNoteCreator creator = medicalNoteProvider.getFactory(request.getNoteType());
        MedicalNote note = creator.createNote(request);
        note.setStatus(MedicalNoteStatus.Create);
        note.setVisitNumber(getVisitNumber(request.getCaseId()));
        MedicalNote created = medicalNoteRepository.save(note);
        return MedicalResponse.builder()
                .medicalNotId(created.getId())
                .visitNumber(note.getVisitNumber().toString())
                .build();
    }

    private Integer getVisitNumber(Long caseId) {
        Integer visitNumber = medicalNoteRepository.findMaxVisitByCaseId(caseId);
        if (visitNumber == null)
            return 1;
        else
            return visitNumber + 1;
    }

}
