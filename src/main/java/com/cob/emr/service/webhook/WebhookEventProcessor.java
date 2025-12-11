package com.cob.emr.service.webhook;

public interface WebhookEventProcessor<T> {
    void process(T payload);
    String getSupportedEventType();
}
