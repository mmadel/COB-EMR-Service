package com.cob.emr.service.webhook;

import com.cob.emr.model.patient.chart.note.PatientChartNoteRequest;
import com.cob.emr.model.webhook.DailyNoteCreatedWebhookPayload;
import com.cob.emr.service.patient.chart.note.PatientCaseNoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DailyNoteCreatedProcessor implements WebhookEventProcessor<DailyNoteCreatedWebhookPayload> {
    private final PatientCaseNoteService patientCaseNoteService;

    @Transactional
    @Override
    public void process(DailyNoteCreatedWebhookPayload payload) {
        log.info("Processing daily_note.created event for noteId: {}", payload.getNoteId());

        try {
            patientCaseNoteService.create(PatientChartNoteRequest
                    .builder()
                    .patientCaseUuid(payload.getPatientCaseId())
                    .noteId(payload.getNoteId())
                    .noteStatus(payload.getNoteStatus())
                    .noteType(payload.getNoteType().equals("DAILY")? "DAILY_NOTE":payload.getNoteType())
                    .encounterDate(payload.getEncounterDate())
                    .build());

            log.info("Successfully added daily note {} to patient chart for case {}",
                    payload.getNoteId(),
                    payload.getPatientCaseId());

        } catch (Exception e) {
            log.error("Failed to process daily_note.created event for noteId: {}",
                    payload.getNoteId(), e);
            throw e;
        }
    }

    @Override
    public String getSupportedEventType() {
        return "daily_note.created";
    }
}
