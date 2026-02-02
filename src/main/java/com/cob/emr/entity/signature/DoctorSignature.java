package com.cob.emr.entity.signature;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "doctor_signature")
@Getter
@Setter
public class DoctorSignature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "signature_token_id", nullable = false)
    private DoctorSignatureToken signatureToken;

    @Column(name = "signature_data", nullable = false, columnDefinition = "LONGTEXT")
    private String signatureData;

    @Column(name = "capture_method", nullable = false)
    private String captureMethod;

    @Column(name = "captured_at", nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime capturedAt;

    @Column(name = "user_agent", length = 1024)
    private String userAgent;

    @Column(name = "screen_resolution")
    private String screenResolution;

    @Column(name = "device_pixel_ratio")
    private Double devicePixelRatio;

    @Column(name = "platform")
    private String platform;

    @Column(name = "sha256_hash", nullable = false)
    private String sha256Hash;

    @Column(name = "font_family")
    private String fontFamily;

    @Column(name = "created_at", columnDefinition = "DATETIME")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }
}
