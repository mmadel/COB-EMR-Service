package com.cob.emr.ddd.domain.medicalnote.model.billing;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UntimedCode {
    private final String cptCode;
    private final String description;
    
    public void validate() {
        if (cptCode == null || cptCode.trim().isEmpty()) {
            throw new IllegalStateException("CPT code is required");
        }
    }
    
    public boolean isEvaluationCode() {
        return cptCode != null && cptCode.matches("9716[1-4]");
    }
}
