package com.cob.emr.usecases.patient.payment;

import com.cob.emr.repositories.patient.payment.PatientCasePaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeletePatientCasePaymentUseCase {
    @Autowired
    PatientCasePaymentRepository patientCasePaymentRepository;

    public void delete(Long id) {
        patientCasePaymentRepository.deleteById(id);
    }
}
