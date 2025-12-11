package com.cob.emr.model.webhook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;

@Data
public class NoteCreatedWebhookPayload {

    @JsonProperty("event_id")
    private String eventId;

    @JsonProperty("event_type")
    private String eventType;

    @JsonProperty("occurred_at")
    private Instant occurredAt;

    @JsonProperty("note")
    private NoteData note;

    @Data
    public static class NoteData {
        @JsonProperty("note_id")
        private String noteId;

        @JsonProperty("patient_id")
        private Long patientId;

        @JsonProperty("case_id")
        private Long caseId;

        @JsonProperty("note_type")
        private String noteType;

        @JsonProperty("status")
        private String status;
    }
}