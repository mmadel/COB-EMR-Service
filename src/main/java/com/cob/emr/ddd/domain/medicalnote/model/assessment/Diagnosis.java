package com.cob.emr.ddd.domain.medicalnote.model.assessment;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class Diagnosis {
    private final String assessmentDiagnosis;
    private final List<String> icdCodes;
}
