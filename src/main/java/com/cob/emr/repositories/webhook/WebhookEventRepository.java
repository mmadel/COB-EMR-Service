package com.cob.emr.repositories.webhook;

import com.cob.emr.entity.webhook.WebhookEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface WebhookEventRepository extends JpaRepository<WebhookEvent, String> {
    List<WebhookEvent> findByStatus(String status);

    // Find failed events that can be retried
    @Query("SELECT w FROM WebhookEvent w WHERE w.status = 'FAILED' " +
            "AND w.retryCount < :maxRetries " +
            "ORDER BY w.receivedAt ASC")
    List<WebhookEvent> findRetryableEvents(@Param("maxRetries") int maxRetries);

    // Find events by type and status
    List<WebhookEvent> findByEventTypeAndStatus(String eventType, String status);

    // Find events received after a certain time
    List<WebhookEvent> findByReceivedAtAfter(Instant after);

    // Find events by status and received date range
    @Query("SELECT w FROM WebhookEvent w WHERE w.status = :status " +
            "AND w.receivedAt BETWEEN :startDate AND :endDate " +
            "ORDER BY w.receivedAt DESC")
    List<WebhookEvent> findByStatusAndDateRange(
            @Param("status") String status,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate
    );

    // Count events by status
    long countByStatus(String status);

    // Count failed events
    @Query("SELECT COUNT(w) FROM WebhookEvent w WHERE w.status = 'FAILED' " +
            "AND w.retryCount >= :maxRetries")
    long countPermanentlyFailedEvents(@Param("maxRetries") int maxRetries);
}
