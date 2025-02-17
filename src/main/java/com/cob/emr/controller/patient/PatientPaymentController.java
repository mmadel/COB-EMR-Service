package com.cob.emr.controller.patient;

import com.cob.emr.response.ResponseHandler;
import com.cob.emr.usecases.appointment.patient.payment.FindPatientPaymentRecordsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/patient/payment")
public class PatientPaymentController {
    @Autowired
    FindPatientPaymentRecordsUseCase findPatientPaymentRecordsUseCase;

    @GetMapping("/find/patientId/{patientId}/caseId/{caseId}")
    public ResponseEntity<Object> find(@PathVariable Long patientId,@PathVariable Long caseId, @RequestParam(name = "offset") String offset,
                                       @RequestParam(name = "limit") String limit) {
        Pageable paging = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit));
        return ResponseHandler
                .generateResponse("Successfully find patient payments",
                        HttpStatus.OK, null,
                        findPatientPaymentRecordsUseCase.find(patientId,caseId, paging));
    }
}
