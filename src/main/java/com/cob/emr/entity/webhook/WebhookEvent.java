package com.cob.emr.entity.webhook;

import com.cob.emr.enums.WebhookEventStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(
        name = "webhook_events",
        indexes = {
                @Index(name = "idx_event_type", columnList = "event_type"),
                @Index(name = "idx_status", columnList = "status"),
                @Index(name = "idx_received_at", columnList = "received_at"),
                @Index(name = "idx_status_retry", columnList = "status, retry_count")
        }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebhookEvent {

    @Id
    @Column(name = "event_id", length = 100)
    private String eventId;

    @Column(name = "event_type", nullable = false, length = 50)
    private String eventType;

    @Column(name = "received_at", nullable = false)
    private Instant receivedAt;

    @Column(name = "processed_at")
    private Instant processedAt;

    @Column(name = "status", nullable = false, length = 20)
    private String status; // PROCESSING, PROCESSED, FAILED, UNSUPPORTED

    @Column(name = "payload", columnDefinition = "TEXT")
    private String payload;

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

    @Column(name = "retry_count", nullable = false)
    @Builder.Default
    private Integer retryCount = 0;

    @Column(name = "last_retry_at")
    private Instant lastRetryAt;

    @Version
    @Column(name = "version")
    private Long version;

    @PrePersist
    protected void onCreate() {
        if (receivedAt == null) {
            receivedAt = Instant.now();
        }
        if (retryCount == null) {
            retryCount = 0;
        }
        if (status == null) {
            status = WebhookEventStatus.PROCESSING.name();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        if (status != null && status.equals(WebhookEventStatus.FAILED.name())) {
            lastRetryAt = Instant.now();
        }
    }

    // Helper methods
    public void markAsProcessed() {
        this.status = WebhookEventStatus.PROCESSED.name();
        this.processedAt = Instant.now();
    }

    public void markAsFailed(String errorMessage) {
        this.status = WebhookEventStatus.FAILED.name();
        this.errorMessage = errorMessage;
        this.retryCount++;
        this.lastRetryAt = Instant.now();
    }

    public void markAsUnsupported(String reason) {
        this.status = WebhookEventStatus.UNSUPPORTED.name();
        this.errorMessage = reason;
        this.processedAt = Instant.now();
    }

    public boolean canRetry(int maxRetries) {
        return this.retryCount < maxRetries
                && WebhookEventStatus.FAILED.name().equals(this.status);
    }
}
