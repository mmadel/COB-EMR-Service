package com.cob.emr.model.appointment;

import com.cob.emr.enums.AppointmentStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppointmentStatusHistoryModel {
    private Long id;

    private AppointmentStatus status;

    private Long createdDate;

    private StatusHistoryValueModel statusHistoryValueModel;
}
