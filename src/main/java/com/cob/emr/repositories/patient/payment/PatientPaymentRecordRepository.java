package com.cob.emr.repositories.patient.payment;

import com.cob.emr.entity.patient.PatientPaymentRecord;
import org.springframework.data.repository.CrudRepository;

public interface PatientPaymentRecordRepository extends CrudRepository<PatientPaymentRecord, Long> {

}

