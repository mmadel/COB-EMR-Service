package com.cob.emr.entity.signature;

import com.cob.emr.enums.ConsentMethod;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "signature_capture_metadata")
@Getter
@Setter
public class SignatureCaptureMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "signature_id")
    private Long signatureId;

    @ManyToOne
    @JoinColumn(name = "signature_token_id", nullable = false)
    private DoctorSignatureToken signatureToken;

    @ManyToOne
    @JoinColumn(name = "consent_version_id")
    private ConsentTextVersion consentVersion;

    @Column(name = "consent_display_started_at")
    private LocalDateTime consentDisplayStartedAt;

    @Column(name = "consent_display_duration_ms")
    private Long consentDisplayDurationMs;

    @Enumerated(EnumType.STRING)
    @Column(name = "consent_method")
    private ConsentMethod consentMethod;

    @Column(name = "consent_client_text_hash")
    private String consentClientTextHash;

    @Column(name = "consent_acknowledged_at")
    private LocalDateTime consentAcknowledgedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
