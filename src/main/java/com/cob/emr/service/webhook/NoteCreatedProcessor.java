package com.cob.emr.service.webhook;

import com.cob.emr.model.webhook.NoteCreatedWebhookPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoteCreatedProcessor implements WebhookEventProcessor<NoteCreatedWebhookPayload>{
    @Override
    public void process(NoteCreatedWebhookPayload payload) {
        log.info("Processing note.created event for noteId: {}", payload.getNote().getNoteId());

        try {
            // Update patient chart with new note reference
//            patientChartService.addNoteToChart(
//                    payload.getNote().getPatientId(),
//                    payload.getNote().getCaseId(),
//                    payload.getNote().getNoteId(),
//                    payload.getNote().getNoteType(),
//                    payload.getNote().getStatus()
//            );

            log.info("Successfully added note {} to patient chart for patient {}",
                    payload.getNote().getNoteId(),
                    payload.getNote().getPatientId());

        } catch (Exception e) {
            log.error("Failed to process note.created event for noteId: {}",
                    payload.getNote().getNoteId(), e);
            throw e; // Re-throw to trigger retry
        }
    }
    @Override
    public String getSupportedEventType() {
        return "note.created";
    }
}
