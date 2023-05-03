package com.cob.emr.model.patient.cases;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CaseInsurance {
    private Long id;

    private String insuranceCompanyName;

    private String insuranceIdNumber;

    private String groupNumber;
}
