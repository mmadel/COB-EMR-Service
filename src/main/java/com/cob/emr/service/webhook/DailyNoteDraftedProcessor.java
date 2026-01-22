package com.cob.emr.service.webhook;

import com.cob.emr.model.patient.chart.note.PatientChartNoteRequest;
import com.cob.emr.model.webhook.DailyNoteDraftedWebhookPayload;
import com.cob.emr.service.patient.chart.note.PatientCaseNoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DailyNoteDraftedProcessor implements WebhookEventProcessor<DailyNoteDraftedWebhookPayload> {
    private final PatientCaseNoteService patientCaseNoteService;

    @Transactional
    @Override
    public void process(DailyNoteDraftedWebhookPayload payload) {
        log.info("Processing daily_note.drafted event for noteId: {}", payload.getNoteId());

        try {
            patientCaseNoteService.update(PatientChartNoteRequest
                    .builder()
                    .patientCaseUuid(payload.getPatientCaseId())
                    .noteType(payload.getNoteType())
                    .noteStatus(payload.getNoteStatus())
                    .noteId(payload.getNoteId())
                    .build());

            log.info("Successfully updated daily note {} status to {}",
                    payload.getNoteId(),
                    payload.getNoteStatus());

        } catch (Exception e) {
            log.error("Failed to process daily_note.drafted event for noteId: {}",
                    payload.getNoteId(), e);
            throw e;
        }
    }

    @Override
    public String getSupportedEventType() {
        return "daily_note.drafted";
    }
}
