package com.cob.emr.repositories.patient.insurance;

import com.cob.emr.entity.patient.PatientInsurance;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PatientInsuranceRepository  extends PagingAndSortingRepository<PatientInsurance, Long> {
    List<PatientInsurance> findByPatient_Id(Long id);
}
