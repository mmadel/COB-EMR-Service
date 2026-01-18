package com.cob.emr.service.webhook;

import com.cob.emr.model.webhook.NoteDeletedWebhookPayload;
import com.cob.emr.service.patient.chart.note.PatientCaseNoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoteDeletedProcessor implements WebhookEventProcessor<NoteDeletedWebhookPayload> {
    private final PatientCaseNoteService patientCaseNoteService;

    @Transactional
    @Override
    public void process(NoteDeletedWebhookPayload payload) {
        log.info("Processing note.deleted event for noteId: {}", payload.getNoteId());

        try {
            patientCaseNoteService.delete(payload.getNoteId());

            log.info("Successfully deleted note {}", payload.getNoteId());

        } catch (Exception e) {
            log.error("Failed to process note.deleted event for noteId: {}",
                    payload.getNoteId(), e);
            throw e;
        }
    }

    @Override
    public String getSupportedEventType() {
        return "note.deleted";
    }
}
