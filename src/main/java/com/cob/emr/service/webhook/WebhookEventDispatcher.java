package com.cob.emr.service.webhook;


import com.cob.emr.model.webhook.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WebhookEventDispatcher {

    private final Map<String, WebhookEventProcessor<?>> processors;
    private final ObjectMapper objectMapper;

    public WebhookEventDispatcher(List<WebhookEventProcessor<?>> processorList,
                                  ObjectMapper objectMapper) {
        this.processors = processorList.stream()
                .collect(Collectors.toMap(
                        WebhookEventProcessor::getSupportedEventType,
                        Function.identity()
                ));
        this.objectMapper = objectMapper;
    }

    public void dispatch(String eventType, String jsonPayload) throws Exception {
        log.info("Dispatching webhook event: {}", eventType);

        WebhookEventProcessor<?> processor = processors.get(eventType);

        if (processor == null) {
            log.warn("No processor found for event type: {}", eventType);
            throw new IllegalArgumentException("Unsupported event type: " + eventType);
        }

        processTypedEvent(processor, eventType, jsonPayload);
    }

    @SuppressWarnings("unchecked")
    private <T> void processTypedEvent(WebhookEventProcessor<T> processor,
                                       String eventType,
                                       String jsonPayload) throws Exception {
        Class<T> payloadClass = getPayloadClass(eventType);
        T payload = objectMapper.readValue(jsonPayload, payloadClass);
        processor.process(payload);
    }

    @SuppressWarnings("unchecked")
    private <T> Class<T> getPayloadClass(String eventType) {
        switch (eventType){
            case "note.created":
                return (Class<T>) NoteCreatedWebhookPayload.class;
            case "note.drafted":
                return (Class<T>) NoteDraftedWebhookPayload.class;
            case "note.finalized":
                return (Class<T>) NoteFinalizedWebhookPayload.class;
            case "note.deleted":
                return (Class<T>) NoteDeletedWebhookPayload.class;
            case "note.diagnosis.assigned":
                return (Class<T>) NoteAssignedDiagnosisWebhookPayload.class;
            case "daily_note.created":
                return (Class<T>) DailyNoteCreatedWebhookPayload.class;
            case "daily_note.drafted":
                return (Class<T>) DailyNoteDraftedWebhookPayload.class;
            default:
                throw new IllegalArgumentException("Unknown event type: " + eventType);
        }
    }
}
