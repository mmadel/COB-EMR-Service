package com.cob.emr.model.calendar;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CalendarsUpdateModel {
    private List<CalendarModel> markAsAttached;
    private List<CalendarModel> markAsUnAttached;

    private List<CalendarModel> markAsPublic;
    private List<CalendarModel> markAsNotPublic;
    private Long clinicId;
    private String uuid;
}
