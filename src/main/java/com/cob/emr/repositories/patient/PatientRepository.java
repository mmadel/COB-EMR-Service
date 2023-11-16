package com.cob.emr.repositories.patient;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.patient.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends PagingAndSortingRepository<Patient, Long> {

    Page<Patient> findByClinicsIn(Pageable pageable, List<Clinic> clinics);

    List<Patient> findByIdAndClinicsIn(Long patientId, List<Clinic> clinics);
    @Query("SELECT p FROM patient p JOIN p.clinics c WHERE :clinic IN c")
    List<Patient> findByClinic(@Param("clinic") Clinic clinic);
}
