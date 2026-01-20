package com.cob.emr.service.patient.cases;

import com.cob.emr.entity.patient.PatientCase;
import com.cob.emr.entity.patient.chart.note.PatientChartNote;
import com.cob.emr.model.patient.cases.CaseDiagnosis;
import com.cob.emr.model.webhook.valueobject.Diagnosis;
import com.cob.emr.repositories.patient.cases.PatientCaseRepository;
import com.cob.emr.repositories.patient.chart.note.PatientChartNoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientCaseDiagnosisUpdaterService {
    private final PatientChartNoteRepository patientChartNoteRepository;
    private final PatientCaseRepository patientCaseRepository;

    @Transactional
    public void updateDiagnosesFromNote(String noteId, List<Diagnosis> icdDiagnoses) {
        Optional<PatientChartNote> chartNoteOpt = patientChartNoteRepository.findByNoteId(noteId);
        if (chartNoteOpt.isEmpty()) {
            log.warn("No PatientChartNote found for noteId: {}", noteId);
            return;
        }

        String patientCaseUuid = chartNoteOpt.get().getPatientCaseUuid();
        Optional<PatientCase> patientCaseOpt = patientCaseRepository.findByUuid(patientCaseUuid);
        if (patientCaseOpt.isEmpty()) {
            log.warn("No PatientCase found for uuid: {}", patientCaseUuid);
            return;
        }

        PatientCase patientCase = patientCaseOpt.get();
        List<CaseDiagnosis> existingDiagnoses = patientCase.getCaseDiagnosis();
        if (existingDiagnoses == null) {
            existingDiagnoses = new ArrayList<>();
        }

        Set<String> existingCodes = existingDiagnoses.stream()
                .map(CaseDiagnosis::getDiagnosisCode)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        List<CaseDiagnosis> newDiagnoses = icdDiagnoses.stream()
                .filter(diagnosis -> !existingCodes.contains(diagnosis.getCode().toLowerCase()))
                .map(this::toCaseDiagnosis)
                .collect(Collectors.toList());

        if (!newDiagnoses.isEmpty()) {
            existingDiagnoses.addAll(newDiagnoses);
            patientCase.setCaseDiagnosis(existingDiagnoses);
            patientCaseRepository.save(patientCase);
            log.info("Added {} new diagnoses to patient case uuid: {}", newDiagnoses.size(), patientCaseUuid);
        } else {
            log.info("No new diagnoses to add for patient case uuid: {}", patientCaseUuid);
        }
    }

    private CaseDiagnosis toCaseDiagnosis(Diagnosis diagnosis) {
        CaseDiagnosis caseDiagnosis = new CaseDiagnosis();
        caseDiagnosis.setDiagnosisCode(diagnosis.getCode());
        caseDiagnosis.setDiagnosisDescription(diagnosis.getDescription());
        return caseDiagnosis;
    }
}
