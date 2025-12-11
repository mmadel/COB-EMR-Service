package com.cob.emr.ddd.domain.medicalnote.model.objective;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PerformedIntervention {
    private final String cptCode;
    private final String description;
    private final Integer durationMinutes;
    private final Integer units;
    
    public boolean isEvaluationCode() {
        return cptCode != null && cptCode.matches("9716[1-4]");
    }
}
