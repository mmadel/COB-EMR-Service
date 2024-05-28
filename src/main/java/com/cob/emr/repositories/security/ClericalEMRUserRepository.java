package com.cob.emr.repositories.security;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.security.user.ClericalEMRUser;
import com.cob.emr.entity.security.user.ClinicalEMRUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClericalEMRUserRepository extends JpaRepository<ClericalEMRUser, Long> {
    Page<ClericalEMRUser> findByClinicsIn(Pageable pageable, List<Clinic> clinics);
}
