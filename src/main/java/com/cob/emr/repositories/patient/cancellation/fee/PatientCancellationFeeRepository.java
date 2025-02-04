package com.cob.emr.repositories.patient.cancellation.fee;

import com.cob.emr.entity.patient.PatientCancellationFee;
import org.springframework.data.repository.CrudRepository;

public interface PatientCancellationFeeRepository extends CrudRepository<PatientCancellationFee, Long> {
}
