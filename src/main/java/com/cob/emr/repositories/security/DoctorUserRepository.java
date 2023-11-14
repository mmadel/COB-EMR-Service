package com.cob.emr.repositories.security;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.security.user.DoctorUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DoctorUserRepository extends JpaRepository<DoctorUser, Long> {
    Page<DoctorUser> findByClinicsIn(Pageable pageable, List<Clinic> clinics);
    @Modifying
    @Query("delete from doctor_user du where du.uuid =:uuid")
    void deleteByUUID(@Param("uuid") String uuid);
}
