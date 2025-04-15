package com.cob.emr.model.medical.note;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QuickDischargeRequest {
    private Long dischargeDate;
    private Integer numberOfVisits;
    private String comment;
}
