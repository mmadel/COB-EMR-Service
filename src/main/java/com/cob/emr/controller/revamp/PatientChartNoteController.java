package com.cob.emr.controller.revamp;

import com.cob.emr.model.response.patient.PatientCaseNoteActionResponse;
import com.cob.emr.usecases.patient.record.revamp.FindPatientCaseNoteActions;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/patient-chart-note")
@RequiredArgsConstructor
public class PatientChartNoteController {

    private final FindPatientCaseNoteActions findPatientCaseNoteActions;

    @GetMapping("/find/actions/case-id/{caseId}")
    public ResponseEntity<List<PatientCaseNoteActionResponse>> find(@PathVariable("caseId") String caseId) {
        List<PatientCaseNoteActionResponse> actions = findPatientCaseNoteActions.find(caseId);
        return ResponseEntity.ok(actions);
    }
}
