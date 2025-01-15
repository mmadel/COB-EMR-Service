package com.cob.emr.model.calendar;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CalendarUpdateAttributeModel {
    private List<CalendarAttributesModel> calendarAttributes;
    private String uuid;
}
