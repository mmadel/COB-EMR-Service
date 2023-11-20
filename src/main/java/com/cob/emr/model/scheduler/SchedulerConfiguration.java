package com.cob.emr.model.scheduler;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Setter
@Getter
public class SchedulerConfiguration {
    private Long id;

    private Long startHour;
    private Long endHour;

    private Long clinicId;
}
