package com.cob.emr.ddd.domain.medicalnote.model.assessment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConsentToCare {
    private final boolean agreesToTreatmentPlan;
    private final String consentDetails;
    
    public void validate() {
        if (!agreesToTreatmentPlan) {
            throw new IllegalStateException("Patient must consent to treatment");
        }
    }
}
