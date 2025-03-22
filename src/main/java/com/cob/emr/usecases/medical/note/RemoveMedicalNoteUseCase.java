package com.cob.emr.usecases.medical.note;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.entity.patient.PatientCase;
import com.cob.emr.enums.MedicalNoteStatus;
import com.cob.emr.model.patient.cases.PatientCaseModel;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import com.cob.emr.repositories.patient.cases.PatientCaseRepository;
import com.cob.emr.usecases.patient.record.AssignPatientCaseRecordActionsUseCase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class RemoveMedicalNoteUseCase {
    @Autowired
    MedicalNoteRepository medicalNoteRepository;
    @Autowired
    PatientCaseRepository patientCaseRepository;
    @Autowired
    AssignPatientCaseRecordActionsUseCase assignPatientCaseRecordActionsUseCase;
    @Autowired
    ModelMapper mapper;
    @Transactional
    public PatientCaseModel remove(Long Id) {
        MedicalNote medicalNote = medicalNoteRepository.findById(Id)
                .orElseThrow(() -> new IllegalArgumentException("Medical Note is not found"));
        medicalNote.setStatus(MedicalNoteStatus.Remove);
        medicalNoteRepository.save(medicalNote);

        PatientCase patientCase = patientCaseRepository.findById(medicalNote.getPatientCase().getId())
                .orElseThrow(() -> new IllegalArgumentException("Patient case is not found"));
        PatientCaseModel caseModel = mapper.map(patientCase, PatientCaseModel.class);
        List<String> actions = assignPatientCaseRecordActionsUseCase.assign(patientCase.getId());
        caseModel.setPatientRecordActions(actions);
        return caseModel;
    }
}
