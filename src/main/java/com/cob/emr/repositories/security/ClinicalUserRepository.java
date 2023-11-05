package com.cob.emr.repositories.security;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.security.user.ClinicalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClinicalUserRepository extends JpaRepository<ClinicalUser, Long> {

    @Query("select cu.clinics from clinical_user cu where cu.uuid =:uuid")
    List<Clinic> findUserClinicsByUUID(@Param("uuid") String uuid);
}
