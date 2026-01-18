package com.cob.emr.usecases.patient.record.revamp;

import com.cob.emr.enums.PatientRecordAction;
import com.cob.emr.repositories.patient.chart.note.PatientChartNoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class FindPatientCaseNoteActions {
    private static final String INITIAL_EXAM = "INITIAL_EXAM";
    private final PatientChartNoteRepository patientChartNoteRepository;

    public List<String> find(String patientCaseUuid) {
        // 1. Case not found → exception
        if (!patientChartNoteRepository.existsByPatientCaseUuid(patientCaseUuid)) {
            return handleCaseWithoutInitialExam();
        }

        // 2. Case has INITIAL_EXAM
        if (patientChartNoteRepository.existsByPatientCaseUuidAndNoteType(patientCaseUuid, INITIAL_EXAM)) {
            return handleCaseWithInitialExam();
        }

        // 3. Case exists but no INITIAL_EXAM
        return handleCaseWithoutInitialExam();
    }

    private List<String> handleCaseWithoutInitialExam() {
        return List.of(
                PatientRecordAction.Initial_Examination.toString(),
                PatientRecordAction.Quick_Discharge.toString(),
                PatientRecordAction.Case_Note.toString()
        );
    }
    private List<String> handleCaseWithInitialExam() {
        return List.of(
                PatientRecordAction.Daily_Note.toString(),
                PatientRecordAction.Progress_Note.toString(),
                PatientRecordAction.Discharge.toString(),
                PatientRecordAction.Quick_Discharge.toString(),
                PatientRecordAction.Case_Note.toString()
        );
    }
}
