package com.cob.emr.ddd.domain.medicalnote.model.planofcare;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;

@Getter
@Builder
public class TreatmentPlan {
    private final String planCode;
    private final DurationCode duration;
    private final FrequencyCode frequency;
    private final LocalDate dischargeDate;
    private final String reason;
    
    public void validate() {
        if (duration == null) {
            throw new IllegalStateException("Treatment duration is required");
        }
        if (frequency == null) {
            throw new IllegalStateException("Treatment frequency is required");
        }
    }
}
