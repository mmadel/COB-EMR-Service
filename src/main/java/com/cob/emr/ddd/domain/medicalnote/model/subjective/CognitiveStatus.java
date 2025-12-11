package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CognitiveStatus {
    private final boolean cognitiveImpairmentSuspected;
    private final String observations;
}
