package com.cob.emr.usecases.patient.record.revamp;

import com.cob.emr.enums.PatientRecordAction;
import com.cob.emr.model.response.patient.PatientCaseNoteActionResponse;
import com.cob.emr.repositories.patient.chart.note.PatientChartNoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FindPatientCaseNoteActions {
    private static final String INITIAL_EXAM = "INITIAL_EXAM";

    private final PatientChartNoteRepository patientChartNoteRepository;

    public List<PatientCaseNoteActionResponse> find(String patientCaseUuid) {
        boolean hasInitialExam = patientChartNoteRepository
                .existsByPatientCaseUuidAndNoteType(patientCaseUuid, INITIAL_EXAM);

        return hasInitialExam
                ? getActionsWithInitialExam()
                : getActionsWithoutInitialExam();
    }

    private List<PatientCaseNoteActionResponse> getActionsWithoutInitialExam() {
        return toActionResponses(
                PatientRecordAction.Initial_Examination,
                PatientRecordAction.Quick_Discharge,
                PatientRecordAction.Case_Note
        );
    }

    private List<PatientCaseNoteActionResponse> getActionsWithInitialExam() {
        return toActionResponses(
                PatientRecordAction.Daily_Note,
                PatientRecordAction.Progress_Note,
                PatientRecordAction.Discharge,
                PatientRecordAction.Quick_Discharge,
                PatientRecordAction.Case_Note
        );
    }

    private List<PatientCaseNoteActionResponse> toActionResponses(PatientRecordAction... actions) {
        return Stream.of(actions)
                .map(this::toActionResponse)
                .collect(Collectors.toList());
    }

    private PatientCaseNoteActionResponse toActionResponse(PatientRecordAction action) {
        return PatientCaseNoteActionResponse.builder()
                .key(action.name())
                .label(action.toString())
                .build();
    }
}
