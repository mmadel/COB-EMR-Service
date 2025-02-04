package com.cob.emr.controller.patient;

import com.cob.emr.model.patient.PatientCancellationFeeModel;
import com.cob.emr.usecases.patient.cancellation.fee.CreateCancellationFeeUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/patient/cancellation/fee")
public class PatientCancellationFeeController {
    @Autowired
    CreateCancellationFeeUseCase createCancellationFeeUseCase;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody PatientCancellationFeeModel model) {
        createCancellationFeeUseCase.create(model);
        return ResponseEntity.ok()
                .build();
    }
}
