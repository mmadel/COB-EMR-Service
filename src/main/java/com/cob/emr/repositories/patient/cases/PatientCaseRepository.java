package com.cob.emr.repositories.patient.cases;

import com.cob.emr.entity.patient.PatientCase;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PatientCaseRepository extends PagingAndSortingRepository<PatientCase, Long> {
    List<PatientCase> findByPatient_Id(Long id);
}
