package com.cob.emr.model.patient;

import com.cob.emr.enums.PatientCancellationFeeDurationUnit;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientCancellationFeeModel {
    private Long id;
    private Double amount;
    private Integer duration;
    private Boolean isEnabled;
    private PatientCancellationFeeDurationUnit feeDurationUnit;
    private Long clinicId;
}
