package com.cob.emr.service.patient.authorization;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.entity.patient.PatientCase;
import com.cob.emr.entity.patient.authorization.PatientCaseAuthorization;
import com.cob.emr.enums.MedicalNoteStatus;
import com.cob.emr.enums.MedicalNoteType;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import com.cob.emr.repositories.patient.authorization.PatientCaseAuthorizationRepository;
import com.cob.emr.repositories.patient.cases.PatientCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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

        if (medicalNote.getDateOfService().longValue() >= authorization.getEffectiveStart().longValue())
            updateAuthorizationCounter(medicalNote.getPatientCase().getId());
        if (medicalNote.getDateOfService().longValue() <= authorization.getEffectiveStart().longValue())
            System.out.println("dos == auth effective date , auth counter must not  be increment ");
    }

    public void updateByCase(Long caseId) {
        List<MedicalNote> medicalNotes = medicalNoteRepository.findByPatientCase_Id(caseId)
                .orElseThrow(() -> new IllegalArgumentException("medical notes not found"))
                .stream().filter(medicalNote -> (!medicalNote.getStatus().equals(MedicalNoteStatus.Remove)
                        && medicalNote.getStatus().equals(MedicalNoteStatus.Finalized)) && medicalNote.getType().equals(MedicalNoteType.DAILY_NOTE))
                .collect(Collectors.toList());
        PatientCase patientCase = patientCaseRepository.findById(caseId)
                .orElseThrow(() -> new IllegalArgumentException("Patient Case is not found for id : " + caseId));
        AtomicInteger counter = new AtomicInteger();
        medicalNotes.forEach(note -> {
            List<PatientCaseAuthorization> opt = patientCaseAuthorizationRepository.findByPatientCase_Id(note.getPatientCase().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Authorization is not found for case " + note.getPatientCase().getId()));
            PatientCaseAuthorization authorization = opt.get(0);
            if (note.getDateOfService().longValue() >= authorization.getEffectiveStart().longValue())
                counter.set(counter.get() + 1);
        });
        patientCase.setAuthorizationCounter(counter.longValue());
        patientCaseRepository.save(patientCase);
    }

    private void updateAuthorizationCounter(Long caseId) {
        PatientCase patientCase = patientCaseRepository.findById(caseId)
                .orElseThrow(() -> new IllegalArgumentException("Patient Case is not found for id : " + caseId));
        Long caseAuthorizationCounter = patientCase.getAuthorizationCounter();
        if (caseAuthorizationCounter == null)
            caseAuthorizationCounter = 1L;
        else
            caseAuthorizationCounter = caseAuthorizationCounter + 1;
        patientCase.setAuthorizationCounter(caseAuthorizationCounter);
        patientCaseRepository.save(patientCase);
    }
}
