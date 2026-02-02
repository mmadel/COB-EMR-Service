package com.cob.emr.repositories.audit;

import com.cob.emr.entity.audit.SignatureAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SignatureAuditLogRepository extends JpaRepository<SignatureAuditLog, Long> {

    List<SignatureAuditLog> findByDoctorIdOrderByPerformedAtDesc(Long doctorId);

    List<SignatureAuditLog> findByTokenIdOrderByPerformedAtDesc(Long tokenId);
}
