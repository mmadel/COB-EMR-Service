package com.cob.emr.model.signature;

import com.cob.emr.enums.ConsentMethod;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsentAcknowledgmentDto {
    private boolean acknowledged;
    private Long timestamp;
    private String textVersion;
    private String textHash;
    private ConsentMethod method;
    private Long displayStartedAt;
    private Long displayDurationMs;
}
