package com.cob.emr.repositories.security;

import com.cob.emr.entity.security.user.DoctorUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorUserRepository extends JpaRepository<DoctorUser, Long> {
}
