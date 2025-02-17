package com.cob.emr.model.scheduler;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SchedulerSettings {
    private Long id;
    private String startWeek;
    private String timeInterval;
    private String startDay;
    private String endDay;
    private String appointmentDuration;
    private Long clinicId;
}
