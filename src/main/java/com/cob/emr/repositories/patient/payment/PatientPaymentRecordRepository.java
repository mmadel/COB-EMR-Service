package com.cob.emr.repositories.patient.payment;

import com.cob.emr.entity.patient.PatientPaymentRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PatientPaymentRecordRepository extends PagingAndSortingRepository<PatientPaymentRecord, Long> {
    Page<PatientPaymentRecord> findByPatient_Id(Long patientId, Pageable paging);

}

