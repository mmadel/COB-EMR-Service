package com.cob.emr.repositories.patient.cases;

import com.cob.emr.entity.patient.PatientCase;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PatientCaseRepository extends PagingAndSortingRepository<PatientCase, Long> {
}
