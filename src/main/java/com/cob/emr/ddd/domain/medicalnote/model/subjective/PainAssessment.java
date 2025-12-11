package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PainAssessment {
    private final Integer painScale;
    private final List<AggravatingFactor> aggravatingFactors;
    private final List<AlleviatingFactor> alleviatingFactors;
    private final String painPattern;
    private final String restrictions;

    public void validate() {
        if (painScale != null && (painScale < 0 || painScale > 10)) {
            throw new IllegalArgumentException("Pain scale must be between 0 and 10");
        }
    }

    public boolean isSeverePain() {
        return painScale != null && painScale >= 7;
    }

    public boolean isMildPain() {
        return painScale != null && painScale <= 3;
    }
}
