package com.cob.emr.controller.webhook;

import com.cob.emr.Utils.HmacUtil;
import com.cob.emr.entity.webhook.WebhookEvent;
import com.cob.emr.repositories.webhook.WebhookEventRepository;
import com.cob.emr.service.webhook.WebhookEventDispatcher;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;

@RestController
@RequiredArgsConstructor
@Slf4j
public class WebhookController {

    @Value("${webhook.secret}") // shared secret configured in receiver too
    private String secret;

    private final WebhookEventRepository repo;
    private final ObjectMapper objectMapper;
    private final WebhookEventDispatcher eventDispatcher;



    @PostMapping("/note-events")
    public ResponseEntity<String> handle(@RequestHeader("X-Webhook-Event-Id") String eventId,
                                         @RequestHeader("X-Webhook-Timestamp") long timestamp,
                                         @RequestHeader("X-Webhook-Signature") String signature,
                                         @RequestBody String body) {
        log.info("Received webhook event: {}", eventId);

        // 1) timestamp replay protection (allow +/- 5 minutes)
//        long now = Instant.now().getEpochSecond();
//        if (Math.abs(now - timestamp) > 300) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("stale timestamp");
//        }

        // 2) verify signature
        String expected = "sha256=" + HmacUtil.sign(secret, timestamp + "." + body);
        if (!MessageDigest.isEqual(
                expected.getBytes(StandardCharsets.UTF_8),
                signature.getBytes(StandardCharsets.UTF_8))) {
            log.error("Invalid signature for event: {}", eventId);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid signature");
        }

        // 3) idempotency - if already processed, return 200
        if (repo.existsById(eventId)) {
            return ResponseEntity.ok("already processed");
        }

        // 4) Parse and validate payload
        try {
            JsonNode payload = objectMapper.readTree(body);

            if (!payload.has("event_type")) {
                log.error("Missing event_type in payload for event: {}", eventId);
                return ResponseEntity.badRequest().body("missing event_type");
            }

            String eventType = payload.get("event_type").asText();

            // 5) Save event as RECEIVED before processing
            WebhookEvent event = new WebhookEvent();
            event.setEventId(eventId);
            event.setEventType(eventType);
            event.setReceivedAt(Instant.ofEpochSecond(timestamp));
            event.setPayload(body);
            event.setStatus("PROCESSING");
            repo.save(event);

            // 6) Dispatch to appropriate processor
            try {
                eventDispatcher.dispatch(eventType, body);

                // 7) Mark as processed
                event.setStatus("PROCESSED");
                event.setProcessedAt(Instant.now());
                repo.save(event);

                log.info("Successfully processed event: {}", eventId);
                return ResponseEntity.ok("processed");

            } catch (IllegalArgumentException e) {
                // Unknown event type
                log.warn("Unsupported event type: {} for event: {}", eventType, eventId);
                event.setStatus("UNSUPPORTED");
                event.setErrorMessage(e.getMessage());
                repo.save(event);
                return ResponseEntity.ok("unsupported event type");

            } catch (Exception e) {
                // Processing failed - mark as failed but don't save as processed
                log.error("Failed to process event: {}", eventId, e);
                event.setStatus("FAILED");
                event.setErrorMessage(e.getMessage());
                event.setRetryCount(event.getRetryCount() + 1);
                repo.save(event);

                // Return 500 to trigger sender retry
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("processing error");
            }

        } catch (Exception ex) {
            log.error("Invalid payload for event: {}", eventId, ex);
            return ResponseEntity.badRequest().body("invalid payload");
        }
    }

    private void processBusiness(JsonNode payload) {
        // example: publish to internal queue, update DB, call other services...
    }
}
