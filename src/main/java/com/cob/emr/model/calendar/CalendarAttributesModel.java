package com.cob.emr.model.calendar;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CalendarAttributesModel {
    private Long calendarId;
    private Long userAttachmentId;
    private Boolean isAttach;
    private Boolean isPublic;
}
