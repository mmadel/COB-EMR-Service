package com.cob.emr.controller.patient;

import com.cob.emr.exception.business.PatientCaseException;
import com.cob.emr.usecases.patient.cases.UpdatePatientCaseChartNoteUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/patient/case")
public class PatientCaseController {
    @Autowired
    UpdatePatientCaseChartNoteUseCase updatePatientCaseChartNoteUseCase;

    @PutMapping("/chart-note/{caseId}")
    public ResponseEntity<Void> updateChartNote(@PathVariable Long caseId, @RequestBody String chartNote) throws PatientCaseException {
        updatePatientCaseChartNoteUseCase.update(caseId, chartNote);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
