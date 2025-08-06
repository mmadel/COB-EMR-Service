package com.cob.emr.usecases.medical.note.creator;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.entity.medical.note.types.QuickDischargeNote;
import com.cob.emr.enums.MedicalNoteType;
import com.cob.emr.exception.business.MedicalNoteException;
import com.cob.emr.model.medical.note.MedicalNoteRequest;
import com.cob.emr.repositories.patient.cases.PatientCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuickDischargeCreator implements MedicalNoteCreator{
    @Autowired
    PatientCaseRepository patientCaseRepository;
    @Override
    public MedicalNote createNote(MedicalNoteRequest medicalNoteRequest) throws MedicalNoteException {

        QuickDischargeNote quickDischargeNote = new QuickDischargeNote();
        quickDischargeNote.setPatientCase(patientCaseRepository.findById(medicalNoteRequest.getCaseId())
                .orElseThrow(() -> new IllegalArgumentException("Case not found")));
        quickDischargeNote.setType(MedicalNoteType.QUICK_DISCHARGE_NOTE);
        quickDischargeNote.setCreatedBy(medicalNoteRequest.getCreatedBy());
        quickDischargeNote.setNoteDate(medicalNoteRequest.getNoteDate());
        quickDischargeNote.setDischargeDate(medicalNoteRequest.getQuickDischargeRequest().getDischargeDate());
        quickDischargeNote.setNumberOfVisits(medicalNoteRequest.getQuickDischargeRequest().getNumberOfVisits());
        quickDischargeNote.setComment(medicalNoteRequest.getQuickDischargeRequest().getComment());
        return quickDischargeNote;
    }
}
