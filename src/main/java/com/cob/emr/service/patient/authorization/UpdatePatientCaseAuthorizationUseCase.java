package com.cob.emr.service.patient.authorization;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.entity.patient.PatientCase;
import com.cob.emr.entity.patient.authorization.PatientCaseAuthorization;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import com.cob.emr.repositories.patient.authorization.PatientCaseAuthorizationRepository;
import com.cob.emr.repositories.patient.cases.PatientCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Component
public class UpdatePatientCaseAuthorizationUseCase {
    @Autowired
    MedicalNoteRepository medicalNoteRepository;
    @Autowired
    PatientCaseAuthorizationRepository patientCaseAuthorizationRepository;
    @Autowired
    PatientCaseRepository patientCaseRepository;

    public void update(Long medicalNotId) {
        MedicalNote medicalNote = medicalNoteRepository.findById(medicalNotId)
                .orElseThrow(() -> new IllegalArgumentException("Medical Not is not found ID : " + medicalNotId));
        List<PatientCaseAuthorization> opt = patientCaseAuthorizationRepository.findByPatientCase_Id(medicalNote.getPatientCase().getId())
                .orElseThrow(() -> new IllegalArgumentException("Authorization is not found for case " + medicalNote.getPatientCase().getId()));

        PatientCaseAuthorization authorization = opt.get(0);

        if (medicalNote.getDateOfService().longValue() > authorization.getEffectiveStart().longValue())
            updateAuthorizationCounter(medicalNote.getPatientCase().getId());
        if (medicalNote.getDateOfService().longValue() <= authorization.getEffectiveStart().longValue())
            System.out.println("dos == auth effective date , auth counter must not  be increment ");
    }

    private void updateAuthorizationCounter(Long caseId) {
        PatientCase patientCase = patientCaseRepository.findById(caseId)
                .orElseThrow(() -> new IllegalArgumentException("Patient Case is not found for id : " + caseId));
        Long caseAuthorizationCounter = patientCase.getAuthorizationCounter();
        if (caseAuthorizationCounter == null)
            caseAuthorizationCounter = 1L;
        else
            caseAuthorizationCounter = caseAuthorizationCounter+1;
        patientCase.setAuthorizationCounter(caseAuthorizationCounter);
        patientCaseRepository.save(patientCase);
    }
}
