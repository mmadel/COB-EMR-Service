package com.cob.emr.model.scheduler;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SchedulerSettings {
    private String startWeek;
    private String timeInterval;
    private String startDay;
    private String endDay;
    private String appointmentDuration;
    private Long clinicId;
}
