package com.cob.emr.ddd.domain.medicalnote.model.billing;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TimedCode {
    private final String cptCode;
    private final String description;
    private final int durationMinutes;
    private final int units;
    
    public void validate() {
        if (cptCode == null || cptCode.trim().isEmpty()) {
            throw new IllegalStateException("CPT code is required");
        }
        if (durationMinutes <= 0) {
            throw new IllegalStateException("Duration must be positive");
        }
        if (units <= 0) {
            throw new IllegalStateException("Units must be positive");
        }
    }
    
    public static int calculateUnits(int durationMinutes) {
        if (durationMinutes < 8) return 0;
        return (durationMinutes / 15) + ((durationMinutes % 15 >= 8) ? 1 : 0);
    }
}
