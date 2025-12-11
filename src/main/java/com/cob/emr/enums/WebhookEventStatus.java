package com.cob.emr.enums;

public enum WebhookEventStatus {
    PROCESSING,   // Event received and being processed
    PROCESSED,    // Successfully processed
    FAILED,       // Processing failed, can be retried
    UNSUPPORTED   // Event type not supported, won't be retried
}
