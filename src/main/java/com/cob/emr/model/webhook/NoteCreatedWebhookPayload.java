package com.cob.emr.model.webhook;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class NoteCreatedWebhookPayload {
    private String eventId;
    private String eventType;
    private Instant occurredAt;
    private String noteId;
    private String patientCaseId;
    private String noteType;
    private String noteStatus;
    private LocalDateTime encounterDate;
    private LocalDate dos;

}