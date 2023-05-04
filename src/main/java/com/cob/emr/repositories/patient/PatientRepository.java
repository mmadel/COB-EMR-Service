package com.cob.emr.repositories.patient;

import com.cob.emr.entity.patient.Patient;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PatientRepository extends PagingAndSortingRepository<Patient, Long> {
}
