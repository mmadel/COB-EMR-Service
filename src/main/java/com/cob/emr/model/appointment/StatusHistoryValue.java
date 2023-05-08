package com.cob.emr.model.appointment;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StatusHistoryValue {

    private Long cancelDate;

    private String cancelReason;

    private String comment;
}
