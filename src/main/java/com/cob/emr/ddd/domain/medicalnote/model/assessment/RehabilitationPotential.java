package com.cob.emr.ddd.domain.medicalnote.model.assessment;

import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.RehabPotentialRating;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RehabilitationPotential {
    private final RehabPotentialRating rating;
    private final String justification;
    
    public void validate() {
        if (rating == null) {
            throw new IllegalStateException("Rehabilitation potential rating is required");
        }
    }
}
