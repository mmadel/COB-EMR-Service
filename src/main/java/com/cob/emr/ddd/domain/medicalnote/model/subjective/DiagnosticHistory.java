package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.IcdCode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiagnosticHistory {
    private final List<IcdCode> icdCodes;                    // Changed from String
    private final List<IcdCode> treatmentDiagnosisCodes;
    private final List<String> imagingResults;
}
