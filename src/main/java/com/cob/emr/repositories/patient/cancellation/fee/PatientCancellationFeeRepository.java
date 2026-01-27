package com.cob.emr.repositories.patient.cancellation.fee;

import com.cob.emr.entity.patient.PatientCancellationFee;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PatientCancellationFeeRepository extends CrudRepository<PatientCancellationFee, Long> {
    @Modifying
    @Query(value = "DELETE FROM patient_cancellation_fee WHERE clinic_id = :clinicId", nativeQuery = true)
    void deleteByClinicId(@Param("clinicId") Long clinicId);
}
