package com.cob.emr.repositories.patient.payment;

import com.cob.emr.entity.patient.PatientCasePayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PatientCasePaymentRepository extends JpaRepository<PatientCasePayment, Long> {
    List<PatientCasePayment> findByPatientCase_Id(Long patientCaseId);
    Optional<PatientCasePayment> findByUuid(String uuid);
    void deleteByPatientCase_Id(Long patientCaseId);
}
