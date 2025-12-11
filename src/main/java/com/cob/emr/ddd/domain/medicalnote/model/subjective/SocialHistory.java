package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.TobaccoUseStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class SocialHistory {
    private final TobaccoUseStatus tobaccoUse;
    private final TobaccoUsageDetails tobaccoDetails;
    private final HomeEnvironment homeEnvironment;
    private final List<String> durableMedicalEquipment;
}
