package com.cob.emr.controller.patient;

import com.cob.emr.model.patient.PatientCancellationFeeModel;
import com.cob.emr.usecases.patient.cancellation.fee.CreateCancellationFeeUseCase;
import com.cob.emr.usecases.patient.cancellation.fee.FindCancellationFeeUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/patient/cancellation/fee")
public class PatientCancellationFeeController {
    @Autowired
    CreateCancellationFeeUseCase createCancellationFeeUseCase;
    @Autowired
    FindCancellationFeeUseCase findCancellationFeeUseCase;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody PatientCancellationFeeModel model) {
        createCancellationFeeUseCase.create(model);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/find/clinicId/{clinicId}")
    public ResponseEntity find(@PathVariable("clinicId") Long clinicId) {
        return ResponseEntity.ok()
                .body(findCancellationFeeUseCase.find(clinicId));
    }
}
