package com.cob.emr.repositories.clinic;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.patient.Patient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ClinicRepository extends PagingAndSortingRepository<Clinic, Long> {

    List<Clinic> findByPatientsIn(Collection<Patient> patients);

    @Query("select c from  Clinic c where  c.organization.id =:organizationId")
    List<Clinic> findByOrganization(@Param(value = "organizationId") Long organizationId);
}
