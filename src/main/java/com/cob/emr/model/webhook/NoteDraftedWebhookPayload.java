package com.cob.emr.model.webhook;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoteDraftedWebhookPayload {

    private String eventType;

    private String noteId;

    private Long patientId;

    private String status;

    private LocalDateTime draftedAt;
}
