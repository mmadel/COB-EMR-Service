package com.cob.emr.entity.audit;

import com.cob.emr.enums.SignatureAuditEvent;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "signature_audit_log", indexes = {
        @Index(name = "idx_sig_audit_doctor", columnList = "doctor_id, event_type, performed_at"),
        @Index(name = "idx_sig_audit_token", columnList = "token_id, performed_at"),
        @Index(name = "idx_sig_audit_ip", columnList = "ip_address, performed_at")
})
public class SignatureAuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "doctor_id")
    private Long doctorId;

    @Column(name = "token_id")
    private Long tokenId;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false)
    private SignatureAuditEvent eventType;

    @Column(name = "success", nullable = false)
    private boolean success;

    @Column(name = "error_code", length = 30)
    private String errorCode;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "user_agent", length = 1024)
    private String userAgent;

    @Column(name = "details", columnDefinition = "JSON")
    private String details;

    @Column(name = "performed_by")
    private String performedBy;

    @Column(name = "performed_at", nullable = false)
    private Long performedAt;
}
