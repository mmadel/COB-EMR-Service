package com.cob.emr.model.calendar;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CalendarsListModel {

    private String name;
    private String createdBy;
    private Long userCalendarAttachmentId;
    private Long calendarId;
    private Long clinicId;
    private Boolean isAttach;
    private Boolean isPublic;
}
