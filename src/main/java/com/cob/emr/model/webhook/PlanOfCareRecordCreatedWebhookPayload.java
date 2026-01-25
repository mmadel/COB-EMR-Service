package com.cob.emr.model.webhook;

import lombok.Data;

import java.time.Instant;

@Data
public class PlanOfCareRecordCreatedWebhookPayload {
    private String eventId;
    private String eventType;
    private Instant occurredAt;
    private String planOfCareId;
    private String patientCaseId;
    private String noteId;
}
