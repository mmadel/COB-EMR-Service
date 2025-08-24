package com.cob.emr.usecases.medical.note.creator;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.entity.medical.note.types.FullDischargeNote;
import com.cob.emr.entity.medical.note.types.ProgressNote;
import com.cob.emr.enums.MedicalNoteStatus;
import com.cob.emr.enums.MedicalNoteType;
import com.cob.emr.exception.business.MedicalNoteException;
import com.cob.emr.model.medical.note.MedicalNoteRequest;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import com.cob.emr.repositories.patient.cases.PatientCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FullDischargeNoteCreator implements MedicalNoteCreator{
    @Autowired
    MedicalNoteRepository medicalNoteRepository;
    @Autowired
    PatientCaseRepository patientCaseRepository;
    @Override
    public MedicalNote createNote(MedicalNoteRequest medicalNoteRequest) throws MedicalNoteException {
        List<MedicalNote> medicalNotes = medicalNoteRepository.findRecentNoteByTypeAndStatusAndCase( MedicalNoteType.PROGRESS_NOTE, MedicalNoteStatus.Finalized,medicalNoteRequest.getCaseId());
        if (!medicalNotes.isEmpty()) {
            ProgressNote progressNote =(ProgressNote)medicalNotes.stream().findFirst().get() ;
            if(!progressNote.getStatus().equals(MedicalNoteStatus.Finalized))
                throw new MedicalNoteException(HttpStatus.CONFLICT, MedicalNoteException.FINALIZE_PROGRESS_NOTE, new Object[]{});
            FullDischargeNote fullDischargeNote = new FullDischargeNote();
            fullDischargeNote.setPatientCase(patientCaseRepository.findById(medicalNoteRequest.getCaseId())
                    .orElseThrow(() -> new IllegalArgumentException("Case not found")));
            fullDischargeNote.setType(MedicalNoteType.DISCHARGE_NOTE);
            fullDischargeNote.setCreatedBy(medicalNoteRequest.getCreatedBy());
            fullDischargeNote.setNoteDate(medicalNoteRequest.getNoteDate());
            fullDischargeNote.setSubjective(progressNote.getSubjective());
            fullDischargeNote.setObjective(progressNote.getObjective());
            fullDischargeNote.setBilling(progressNote.getBilling());

            fullDischargeNote.setAssessment(progressNote.getAssessment());
            fullDischargeNote.setPlanOfCare(progressNote.getPlanOfCare());
            return fullDischargeNote;
        }else {
            throw new MedicalNoteException(HttpStatus.CONFLICT, MedicalNoteException.FINALIZE_INIT_EXAM, new Object[]{});
        }
    }
}
