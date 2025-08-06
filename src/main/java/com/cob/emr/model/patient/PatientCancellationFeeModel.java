package com.cob.emr.model.patient;

import com.cob.emr.enums.CancellationFeeDurationUnit;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientCancellationFeeModel {
    private Long id;
    private Double amount;
    private Integer duration;
    private Boolean isEnabled;
    private CancellationFeeDurationUnit feeDurationUnit;
    private Long clinicId;
}
