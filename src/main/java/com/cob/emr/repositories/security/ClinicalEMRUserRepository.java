package com.cob.emr.repositories.security;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.security.user.ClericalEMRUser;
import com.cob.emr.entity.security.user.ClinicalEMRUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClinicalEMRUserRepository extends JpaRepository<ClinicalEMRUser, Long> {
    @Modifying
    @Query("delete from ClericalEMRUser  en where en.id =:id")
    void deleteById(@Param("id") Long id);

    @Query("select cu.clinics from ClinicalEMRUser cu where cu.uuid =:uuid")
    List<Clinic> findUserClinicsByUUID(@Param("uuid") String uuid);

    @Query("select u from ClinicalEMRUser u  where  u.uuid =:uuid")
    Optional<ClinicalEMRUser> findByUUID(@Param("uuid") String uuid);

    Page<ClinicalEMRUser> findByClinicsIn(Pageable pageable, List<Clinic> clinics);

    Optional<ClinicalEMRUser> findByClinicsIn(List<Clinic> clinics);

    @Query("select user from ClinicalEMRUser user JOIN  user.clinics c WHERE :clinicId IN  c.id and user.uuid =:uuid")
    Optional<ClericalEMRUser> findById(@Param("clinicId") Long clinicId, @Param("uuid") String uuid);

}
