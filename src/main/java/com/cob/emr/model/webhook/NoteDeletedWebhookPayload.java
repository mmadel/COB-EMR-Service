package com.cob.emr.model.webhook;

import lombok.Data;

import java.time.Instant;

@Data
public class NoteDeletedWebhookPayload {
    private String eventId;
    private String eventType;
    private Instant occurredAt;
    private String noteId;
}
