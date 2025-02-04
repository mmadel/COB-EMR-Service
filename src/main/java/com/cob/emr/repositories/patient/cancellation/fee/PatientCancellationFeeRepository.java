package com.cob.emr.repositories.patient.cancellation.fee;

import com.cob.emr.entity.patient.PatientCancellationFee;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PatientCancellationFeeRepository extends CrudRepository<PatientCancellationFee, Long> {
    Optional<PatientCancellationFee> findByClinic_Id(Long id);
}
