package com.cob.emr.controller.revamp;

import com.cob.emr.usecases.patient.record.revamp.FindPatientCaseNoteActions;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/patient-chart-note")
@AllArgsConstructor
public class PatientChartNoteController {

    private final FindPatientCaseNoteActions findPatientCaseNoteActions;

    @GetMapping("/find/actions/case-id/{caseId}")
    public ResponseEntity<Object> find(@PathVariable("caseId") Long caseId) {
        List<String> actions = findPatientCaseNoteActions.find(caseId);
        return ResponseEntity.ok().body(actions);
    }
}
