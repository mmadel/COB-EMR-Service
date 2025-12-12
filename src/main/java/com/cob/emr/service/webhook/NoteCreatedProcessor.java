package com.cob.emr.service.webhook;

import com.cob.emr.model.patient.chart.note.PatientChartNoteRequest;
import com.cob.emr.model.webhook.NoteCreatedWebhookPayload;
import com.cob.emr.service.patient.chart.note.PatientCaseNoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoteCreatedProcessor implements WebhookEventProcessor<NoteCreatedWebhookPayload> {
    private final PatientCaseNoteService patientCaseNoteService;

    @Transactional
    @Override
    public void process(NoteCreatedWebhookPayload payload) {
        log.info("Processing note.created event for noteId: {}", payload.getNote().getNoteId());

        try {
            patientCaseNoteService.create(PatientChartNoteRequest
                    .builder()
                    .patientId(payload.getNote().getPatientId())
                    .patientCaseId(payload.getNote().getCaseId())
                    .noteId(payload.getNote().getNoteId())
                    .noteStatus(payload.getNote().getStatus())
                    .noteType(payload.getNote().getNoteType())
                    .encounterDate(payload.getNote().getEncounterDate())
                    .build());

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
