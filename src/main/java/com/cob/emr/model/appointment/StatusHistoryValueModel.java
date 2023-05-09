package com.cob.emr.model.appointment;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StatusHistoryValueModel {

    private Long dateValue;

    private String reason;

    private String comment;
}
