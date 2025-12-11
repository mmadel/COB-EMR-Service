package com.cob.emr.ddd.domain.medicalnote.model.planofcare;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DurationCode {
    private final String code;
    private final Integer weeks;
    
    public static DurationCode fromCode(String code) {
        if (code == null || !code.startsWith("D")) {
            return null;
        }
        try {
            int weeks = Integer.parseInt(code.substring(1));
            return new DurationCode(code, weeks);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
