package com.cob.emr.service.patient.authorization;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.entity.patient.PatientCase;
import com.cob.emr.entity.patient.authorization.PatientCaseAuthorization;
import com.cob.emr.entity.patient.chart.note.PatientChartNote;
import com.cob.emr.enums.MedicalNoteStatus;
import com.cob.emr.enums.MedicalNoteType;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import com.cob.emr.repositories.patient.authorization.PatientCaseAuthorizationRepository;
import com.cob.emr.repositories.patient.cases.PatientCaseRepository;
import com.cob.emr.repositories.patient.chart.note.PatientChartNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class UpdatePatientCaseAuthorizationUseCase {
    private final String FINALIZED_NOTE_STATUS="FINALIZED";
    private final String DAILY_NOTE_TYPE="DAILY_NOTE";
    @Autowired
    MedicalNoteRepository medicalNoteRepository;
    @Autowired
    PatientCaseAuthorizationRepository patientCaseAuthorizationRepository;
    @Autowired
    PatientCaseRepository patientCaseRepository;
    @Autowired
    PatientChartNoteRepository patientChartNoteRepository;

    @Deprecated
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

    public void update(String noteUuid) {
        PatientChartNote chartNote = patientChartNoteRepository.findByNoteId(noteUuid)
                .orElseThrow(() -> new IllegalArgumentException("Chart note not found for noteId: " + noteUuid));

        if (chartNote.getDos() == null)
            return;

        PatientCase patientCase = patientCaseRepository.findByUuid(chartNote.getPatientCaseUuid())
                .orElseThrow(() -> new IllegalArgumentException("Patient Case not found for uuid: " + chartNote.getPatientCaseUuid()));

        List<PatientCaseAuthorization> authorizations = patientCaseAuthorizationRepository.findByPatientCase_Id(patientCase.getId())
                .orElseThrow(() -> new IllegalArgumentException("Authorization not found for case " + chartNote.getPatientCaseUuid()));

        PatientCaseAuthorization authorization = authorizations.get(0);
        long dosMillis = chartNote.getDos().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();

        if (dosMillis >= authorization.getEffectiveStart())
            updateAuthorizationCounter(patientCase.getId());
    }

    public void updateByCase(String caseUuid) {
        PatientCase patientCase = patientCaseRepository.findByUuid(caseUuid)
                .orElseThrow(() -> new IllegalArgumentException("Patient Case is not found for uuid : " + caseUuid));

        List<PatientChartNote> chartNotes = patientChartNoteRepository.findByPatientCaseUuid(caseUuid)
                .orElseThrow(() -> new IllegalArgumentException("Chart notes not found for case " + caseUuid))
                .stream()
                .filter(note -> FINALIZED_NOTE_STATUS.equals(note.getNoteStatus()) && DAILY_NOTE_TYPE.equals(note.getNoteType()) && note.getDos() != null)
                .collect(Collectors.toList());

        List<PatientCaseAuthorization> authorizations = patientCaseAuthorizationRepository.findByPatientCase_Id(patientCase.getId())
                .orElseThrow(() -> new IllegalArgumentException("Authorization is not found for case " + caseUuid));
        PatientCaseAuthorization authorization = authorizations.get(0);

        AtomicInteger counter = new AtomicInteger();
        chartNotes.forEach(note -> {
            long dosMillis = note.getDos().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
            if (dosMillis >= authorization.getEffectiveStart())
                counter.incrementAndGet();
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
