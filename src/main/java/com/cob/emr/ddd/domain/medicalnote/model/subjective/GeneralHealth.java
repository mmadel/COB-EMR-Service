package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeneralHealth {
    private final boolean unexplainedWeightLoss;
    private final String generalHealthStatus;
}
