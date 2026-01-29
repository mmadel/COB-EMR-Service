package com.cob.emr.repositories.audit;

import com.cob.emr.entity.audit.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long>, JpaSpecificationExecutor<AuditLog> {

    Page<AuditLog> findByEntityNameAndEntityId(String entityName, Long entityId, Pageable pageable);

    Page<AuditLog> findByClinicIdAndEntityName(Long clinicId, String entityName, Pageable pageable);

    Page<AuditLog> findByClinicId(Long clinicId, Pageable pageable);

    Page<AuditLog> findByClinicIdAndEntityNameAndEntityId(Long clinicId, String entityName, Long entityId, Pageable pageable);
}
