package com.cob.emr.model.calendar;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CalendarModel {
    private Long id;
    private String name;
    private String createdBy;
    private Boolean isPublic;
    private Boolean attached;
}
