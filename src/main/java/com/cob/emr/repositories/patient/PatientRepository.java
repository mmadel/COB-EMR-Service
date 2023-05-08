package com.cob.emr.repositories.patient;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.patient.Patient;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PatientRepository extends PagingAndSortingRepository<Patient, Long> {

    List<Patient> findByClinicsIn(List<Clinic> clinics);

    List<Patient> findByIdAndClinicsIn(Long patientId, List<Clinic> clinics);
}
