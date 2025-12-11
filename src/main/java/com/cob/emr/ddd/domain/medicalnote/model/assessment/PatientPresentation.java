package com.cob.emr.ddd.domain.medicalnote.model.assessment;

import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.ComplexityLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PatientPresentation {
    private final ComplexityLevel complexityLevel;
    private final String clinicalReason;
    
    public void validate() {
        if (complexityLevel == null) {
            throw new IllegalStateException("Complexity level is required");
        }
    }
}
