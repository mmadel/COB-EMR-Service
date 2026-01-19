package com.cob.emr.service.webhook;

import com.cob.emr.model.patient.chart.note.PatientChartNoteRequest;
import com.cob.emr.model.webhook.NoteFinalizedWebhookPayload;
import com.cob.emr.service.patient.chart.note.PatientCaseNoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoteFinalizedProcessor implements WebhookEventProcessor<NoteFinalizedWebhookPayload> {
    private final PatientCaseNoteService patientCaseNoteService;

    @Transactional
    @Override
    public void process(NoteFinalizedWebhookPayload payload) {
        log.info("Processing note.finalized event for noteId: {}", payload.getNoteId());

        try {
            patientCaseNoteService.update(PatientChartNoteRequest
                    .builder()
                    .patientCaseUuid(payload.getPatientCaseId())
                    .noteType(payload.getNoteType())
                    .noteStatus(payload.getNoteStatus())
                    .noteId(payload.getNoteId())
                    .build());

            log.info("Successfully finalized note {} with status {}",
                    payload.getNoteId(),
                    payload.getNoteStatus());

        } catch (Exception e) {
            log.error("Failed to process note.finalized event for noteId: {}",
                    payload.getNoteId(), e);
            throw e;
        }
    }

    @Override
    public String getSupportedEventType() {
        return "note.finalized";
    }
}
