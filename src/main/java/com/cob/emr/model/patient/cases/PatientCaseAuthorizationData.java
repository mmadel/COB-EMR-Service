package com.cob.emr.model.patient.cases;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class PatientCaseAuthorizationData {
    private Long counter;
    private Integer numberAuthorized;
    private Long effectiveEndtDate;
}
