package com.cob.emr.service.webhook;

import com.cob.emr.model.webhook.NoteCreatedWebhookPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoteDraftedProcessor implements WebhookEventProcessor<NoteCreatedWebhookPayload> {
    @Override
    public void process(NoteCreatedWebhookPayload payload) {

    }

    @Override
    public String getSupportedEventType() {
        return "note.drafted";
    }
}
