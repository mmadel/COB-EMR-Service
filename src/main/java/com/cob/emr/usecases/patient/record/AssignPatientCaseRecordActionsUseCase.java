package com.cob.emr.usecases.patient.record;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.enums.MedicalNoteStatus;
import com.cob.emr.enums.PatientRecordAction;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AssignPatientCaseRecordActionsUseCase {
    @Autowired
    MedicalNoteRepository medicalNoteRepository;

    public List<String> assign(Long caseId) {
        List<MedicalNote> medicalNotes = medicalNoteRepository.findByPatientCase_Id(caseId)
                .orElseThrow(() -> new IllegalArgumentException("medical notes not found"))
                .stream().filter(medicalNote -> !medicalNote.getStatus().equals(MedicalNoteStatus.Remove))
                .collect(Collectors.toList());
        List<String> patientRecordActions = new ArrayList<>();
        if (medicalNotes.isEmpty()) {
            patientRecordActions.add(PatientRecordAction.Initial_Examination.toString());
            patientRecordActions.add(PatientRecordAction.Quick_Discharge.toString());
            patientRecordActions.add(PatientRecordAction.Case_Note.toString());
        } else {
            patientRecordActions.add(PatientRecordAction.Daily_Note.toString());
            patientRecordActions.add(PatientRecordAction.Progress_Note.toString());
            patientRecordActions.add(PatientRecordAction.Discharge.toString());
            patientRecordActions.add(PatientRecordAction.Quick_Discharge.toString());
            patientRecordActions.add(PatientRecordAction.Case_Note.toString());
        }
        return patientRecordActions;
    }
}
