package com.cob.emr.repositories.patient.insurance;

import com.cob.emr.entity.patient.PatientInsurance;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PatientInsuranceRepository  extends PagingAndSortingRepository<PatientInsurance, Long> {
}
