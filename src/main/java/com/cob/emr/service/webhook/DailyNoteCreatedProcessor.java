package com.cob.emr.service.webhook;

import com.cob.emr.model.webhook.DailyNoteCreatedWebhookPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DailyNoteCreatedProcessor implements WebhookEventProcessor<DailyNoteCreatedWebhookPayload> {
    @Override
    public void process(DailyNoteCreatedWebhookPayload payload) {
        System.out.println("DailyNoteCreatedProcessor");
    }

    @Override
    public String getSupportedEventType() {
        return "daily_note.created";
    }
}
