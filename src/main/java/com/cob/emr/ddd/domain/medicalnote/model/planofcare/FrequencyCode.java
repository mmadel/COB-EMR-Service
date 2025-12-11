package com.cob.emr.ddd.domain.medicalnote.model.planofcare;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FrequencyCode {
    private final String code;
    private final Integer timesPerWeek;
    
    public static FrequencyCode fromCode(String code) {
        if (code == null || !code.startsWith("F")) {
            return null;
        }
        try {
            int times = Integer.parseInt(code.substring(1));
            return new FrequencyCode(code, times);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
