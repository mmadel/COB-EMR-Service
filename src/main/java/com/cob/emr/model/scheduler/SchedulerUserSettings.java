package com.cob.emr.model.scheduler;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SchedulerUserSettings {
    private Long clinicId;
    private String user;
    private List<Long> selectedCalendars;
}
