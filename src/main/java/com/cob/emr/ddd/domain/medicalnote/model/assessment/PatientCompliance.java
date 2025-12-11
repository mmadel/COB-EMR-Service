package com.cob.emr.ddd.domain.medicalnote.model.assessment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PatientCompliance {
    private final boolean isCompliant;
    private final ComplianceWithPrecautions complianceWithRestPrecautions;
    private final ComplianceWithPrecautions complianceWithActivityModifications;
}
