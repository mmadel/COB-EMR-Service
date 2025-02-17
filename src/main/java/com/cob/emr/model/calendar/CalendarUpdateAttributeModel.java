package com.cob.emr.model.calendar;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CalendarUpdateAttributeModel {
    private List<CalendarAttachmentAttributesModel> calendarAttachmentAttributes;
    private List<CalendarAccessibilityAttributesModel> calendarAccessibilityAttributes;
    private String uuid;
}
