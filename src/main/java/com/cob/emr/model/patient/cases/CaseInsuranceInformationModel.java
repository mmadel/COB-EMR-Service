package com.cob.emr.model.patient.cases;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CaseInsuranceInformationModel {
    private Boolean isSelfPay;
    private Float selfPayAmount;

    private CaseInsurance primaryInsurance;

    private CaseInsurance secondaryInsurance;

}
