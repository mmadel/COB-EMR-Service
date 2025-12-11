package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MedicalHistory {
    private final GeneralHealth generalHealth;
    private final List<String> currentMedications;
    private final List<String> relevantMedicalConditions;
    private final DiagnosticHistory diagnosticHistory;
    private final SocialHistory socialHistory;
    private final OccupationalHistory occupationalHistory;
    private final FallRiskAssessment fallRiskAssessment;
    private final CognitiveStatus cognitiveStatus;
    private final PelvicFloorHistory pelvicFloorHistory;
}
