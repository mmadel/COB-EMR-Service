package com.cob.emr.service.webhook;

import com.cob.emr.entity.patient.chart.note.PatientChartNote;
import com.cob.emr.model.webhook.PlanOfCareRecordCreatedWebhookPayload;
import com.cob.emr.repositories.patient.chart.note.PatientChartNoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlanOfCareRecordCreatedProcessor implements WebhookEventProcessor<PlanOfCareRecordCreatedWebhookPayload> {

    private final PatientChartNoteRepository patientChartNoteRepository;

    @Transactional
    @Override
    public void process(PlanOfCareRecordCreatedWebhookPayload payload) {
        log.info("Processing planofcare.record.created event for planOfCareId: {}", payload.getPlanOfCareId());

        try {
            PatientChartNote patientChartNote = new PatientChartNote();
            patientChartNote.setPatientCaseUuid(payload.getPatientCaseId());
            patientChartNote.setNoteId(payload.getNoteId());
            patientChartNote.setNoteStatus("CREATED");
            patientChartNote.setNoteType("INITIAL_EXAM_PLAN");
            patientChartNote.setDos(null);

            patientChartNoteRepository.save(patientChartNote);

            log.info("Successfully created PatientChartNote for plan of care {} for case {}",
                    payload.getPlanOfCareId(),
                    payload.getPatientCaseId());

        } catch (Exception e) {
            log.error("Failed to process planofcare.record.created event for planOfCareId: {}",
                    payload.getPlanOfCareId(), e);
            throw e;
        }
    }

    @Override
    public String getSupportedEventType() {
        return "planofcare.record.created";
    }
}
