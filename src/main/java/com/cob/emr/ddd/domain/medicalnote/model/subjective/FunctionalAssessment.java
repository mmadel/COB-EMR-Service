package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FunctionalAssessment {
    private final FunctionalStatus priorFunctionalLevel;
    private final FunctionalStatus currentFunctionalLevel;
    private final String functionalDisabilityScore;

    public int calculateDisabilityScore() {
        if (currentFunctionalLevel == null) return 0;
        return currentFunctionalLevel.calculateDisabilityPoints();
    }
}
