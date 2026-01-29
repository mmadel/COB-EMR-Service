package com.cob.emr.entity.audit;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "audit_log", indexes = {
        @Index(name = "idx_clinic_entity", columnList = "clinic_id, entity_name, entity_id, performed_at"),
        @Index(name = "idx_performed_at", columnList = "performed_at"),
        @Index(name = "idx_clinic_search", columnList = "clinic_id, patient_id, performed_by, performed_at")
})
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "clinic_id")
    private Long clinicId;

    @Column(name = "calendar_id")
    private Long calendarId;

    @Column(name = "patient_id")
    private Long patientId;

    @Column(name = "entity_name", length = 100, nullable = false)
    private String entityName;

    @Column(name = "entity_id", nullable = false)
    private Long entityId;

    @Column(name = "action", length = 20, nullable = false)
    private String action;

    @Column(name = "changes", columnDefinition = "JSON")
    private String changes;

    @Column(name = "performed_by")
    private String performedBy;

    @Column(name = "performed_at", nullable = false)
    private Long performedAt;
}
