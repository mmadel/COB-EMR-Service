package com.cob.emr.model.webhook;

import com.cob.emr.model.webhook.valueobject.Diagnosis;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class NoteAssignedDiagnosisWebhookPayload {
    private String eventId;
    private String eventType;
    private Instant occurredAt;
    private String noteId;
    private List<Diagnosis> icdDiagnoses;
}
