package com.cob.emr.ddd.domain.medicalnote.model.assessment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ComplianceWithPrecautions {
    private final boolean isCompliant;
    private final String details;
}
