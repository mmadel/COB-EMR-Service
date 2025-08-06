package com.cob.emr.repositories.security;

import com.cob.emr.entity.security.user.AdministratorUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdministratorUserRepository  extends JpaRepository<AdministratorUser, Long> {
    @Query("select user from AdministratorUser user WHERE user.uuid =:uuid")
    Optional<AdministratorUser> findById(@Param("uuid") String uuid);
}
