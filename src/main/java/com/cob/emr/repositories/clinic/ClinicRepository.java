package com.cob.emr.repositories.clinic;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.patient.Patient;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;
import java.util.List;

public interface ClinicRepository extends PagingAndSortingRepository<Clinic, Long> {

    List<Clinic> findByPatientsIn(Collection<Patient> patients);
}
