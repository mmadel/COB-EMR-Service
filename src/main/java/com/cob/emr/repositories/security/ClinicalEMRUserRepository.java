package com.cob.emr.repositories.security;

import com.cob.emr.entity.security.user.ClinicalEMRUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicalEMRUserRepository extends JpaRepository<ClinicalEMRUser, Long> {
}
