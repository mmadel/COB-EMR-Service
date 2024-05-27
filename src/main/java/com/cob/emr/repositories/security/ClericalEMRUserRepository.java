package com.cob.emr.repositories.security;

import com.cob.emr.entity.security.user.ClericalEMRUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClericalEMRUserRepository extends JpaRepository<ClericalEMRUser, Long> {
}
