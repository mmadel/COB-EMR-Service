package com.cob.emr.usecases.appointment.patient.payment;

import com.cob.emr.entity.patient.Patient;
import com.cob.emr.entity.patient.PatientCase;
import com.cob.emr.entity.patient.PatientPaymentRecord;
import com.cob.emr.model.patient.payment.PatientPaymentRecordRequest;
import com.cob.emr.repositories.patient.PatientRepository;
import com.cob.emr.repositories.patient.payment.PatientPaymentRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreatePatientPaymentRecordUseCase {
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    PatientPaymentRecordRepository patientPaymentRecordRepository;

    public void create(PatientPaymentRecordRequest request , PatientCase patientCase) {
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found : Id " + request.getPatientId()));
        PatientPaymentRecord record = createEntity(patient, patientCase,request.getAmount(), request.getReason());
        patientPaymentRecordRepository.save(record);
    }

    private PatientPaymentRecord createEntity(Patient patient,PatientCase patientCase, double amount, String reason) {
        PatientPaymentRecord record = new PatientPaymentRecord();
        record.setPatient(patient);
        record.setPatientCase(patientCase);
        record.setAmount(amount);
        record.setReason(reason);
        return record;
    }
}
