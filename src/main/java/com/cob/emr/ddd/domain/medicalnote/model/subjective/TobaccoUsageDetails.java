package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TobaccoUsageDetails {
    private final boolean currentlyUses;
    private final String usageDescription;
    private final boolean recommendationMadeToStop;
    private final boolean adviceProvided;
    private final boolean continuedSupportPlanned;
}
