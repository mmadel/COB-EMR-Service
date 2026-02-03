package com.cob.emr.controller.patient;

import com.cob.emr.exception.business.PatientCaseException;
import com.cob.emr.model.patient.PatientCasePaymentModel;
import com.cob.emr.usecases.patient.payment.CreatePatientCasePaymentUseCase;
import com.cob.emr.usecases.patient.payment.DeletePatientCasePaymentUseCase;
import com.cob.emr.usecases.patient.payment.FindPatientCasePaymentUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/patient/case/payment")
public class PatientCasePaymentController {
    @Autowired
    CreatePatientCasePaymentUseCase createPatientCasePaymentUseCase;
    @Autowired
    FindPatientCasePaymentUseCase findPatientCasePaymentUseCase;
    @Autowired
    DeletePatientCasePaymentUseCase deletePatientCasePaymentUseCase;

    @PostMapping("/create/caseId/{caseId}")
    public ResponseEntity<Object> create(@PathVariable Long caseId, @RequestBody List<PatientCasePaymentModel> models) throws PatientCaseException {
        return new ResponseEntity<>(createPatientCasePaymentUseCase.create(caseId, models), HttpStatus.OK);
    }

    @GetMapping("/find/caseId/{caseId}")
    public ResponseEntity<Object> findByPatientCase(@PathVariable Long caseId) {
        return new ResponseEntity<>(findPatientCasePaymentUseCase.findByPatientCase(caseId), HttpStatus.OK);
    }

    @GetMapping("/find/unpaid/caseId/{caseId}")
    public ResponseEntity<Object> findUnpaidByPatientCase(@PathVariable Long caseId) {
        return new ResponseEntity<>(findPatientCasePaymentUseCase.findUnpaidByPatientCase(caseId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deletePatientCasePaymentUseCase.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
