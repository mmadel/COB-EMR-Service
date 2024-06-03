package com.cob.emr.repositories.security;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.security.user.ClericalEMRUser;
import com.cob.emr.entity.security.user.ClinicalEMRUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClericalEMRUserRepository extends JpaRepository<ClericalEMRUser, Long> {
    Page<ClericalEMRUser> findByClinicsIn(Pageable pageable, List<Clinic> clinics);

    @Query("select user from ClericalEMRUser user JOIN  user.clinics c WHERE :clinicId IN  c.id and user.uuid =:uuid")
    Optional<ClericalEMRUser> findById(@Param("clinicId") Long clinicId, @Param("uuid") String uuid);

    @Query("select u from ClericalEMRUser u  where  u.uuid =:uuid")
    Optional<ClericalEMRUser> findByUUID(@Param("uuid") String uuid);
    void deleteByUuid(String uuid);
}
