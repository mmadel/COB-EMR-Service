package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PelvicFloorHistory {
    private final MenstrualHistory menstrualHistory;
    private final String birthControlMethod;
    private final PregnancyHistory pregnancyHistory;
    private final BladderFunction bladderFunction;
    private final SexualHistory sexualHistory;
    private final boolean hasAbuseHistory;
}
