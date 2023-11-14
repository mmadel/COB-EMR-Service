package com.cob.emr.repositories.security;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.patient.Patient;
import com.cob.emr.entity.security.user.ClinicalUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClinicalUserRepository extends JpaRepository<ClinicalUser, Long> {

    @Query("select cu.clinics from clinical_user cu where cu.uuid =:uuid")
    List<Clinic> findUserClinicsByUUID(@Param("uuid") String uuid);

    Page<ClinicalUser> findByClinicsIn(Pageable pageable, List<Clinic> clinics);

    @Modifying
    @Query("delete from clinical_user cu where cu.uuid =:uuid")
    void deleteByUUID(@Param("uuid") String uuid);
//    @Query("select u from clinical_user u  JOIN u.clinics  where :clinic member of u.clinics and u.uuid =:uuid")
//    Optional<ClinicalUser> findByUUIDAndClinic(@Param("clinic") Clinic clinic,@Param("uuid") String uuid);

    @Query("select u from clinical_user u  where  u.uuid =:uuid")
    Optional<ClinicalUser> findByUUID(@Param("uuid") String uuid);

}
