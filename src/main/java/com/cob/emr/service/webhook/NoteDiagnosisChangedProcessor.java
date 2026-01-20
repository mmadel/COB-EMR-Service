package com.cob.emr.service.webhook;

import com.cob.emr.model.webhook.NoteAssignedDiagnosisWebhookPayload;
import com.cob.emr.service.patient.cases.PatientCaseDiagnosisUpdaterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoteDiagnosisChangedProcessor implements WebhookEventProcessor<NoteAssignedDiagnosisWebhookPayload> {
    private final PatientCaseDiagnosisUpdaterService patientCaseDiagnosisUpdaterService;

    @Override
    public void process(NoteAssignedDiagnosisWebhookPayload payload) {
        log.info("Processing note.diagnosis.assigned event for noteId: {}", payload.getNoteId());
        try {
            patientCaseDiagnosisUpdaterService.updateDiagnosesFromNote(
                    payload.getNoteId(),
                    payload.getIcdDiagnoses()
            );
        } catch (Exception e) {
            log.error("Failed to process note.diagnosis.assigned event for noteId: {}",
                    payload.getNoteId(), e);
            throw e;
        }
    }

    @Override
    public String getSupportedEventType() {
        return "note.diagnosis.assigned";
    }
}
