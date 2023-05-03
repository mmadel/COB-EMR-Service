package com.cob.emr.model.patient.cases;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CaseOtherInformation {
    private Boolean isAuthorized;

    private String addInfoForChart;

    private String autoApplyModifier;
}
