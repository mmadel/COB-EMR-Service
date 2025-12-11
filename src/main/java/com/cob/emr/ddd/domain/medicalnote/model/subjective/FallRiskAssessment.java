package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class FallRiskAssessment {
    private final boolean hasFallHistory;
    private final Integer numberOfFallsInLastYear;
    private final String fallDocumentation;
    
    public boolean isHighRisk() {
        return hasFallHistory && numberOfFallsInLastYear != null && numberOfFallsInLastYear >= 2;
    }
}
