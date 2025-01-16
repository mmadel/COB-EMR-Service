package com.cob.emr.model.response;

import com.cob.emr.model.calendar.CalendarModel;
import com.cob.emr.model.calendar.CalendarsListModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class CalendarResponse {
    Integer number_of_records;
    Integer number_of_matching_records;
    List<CalendarsListModel> records;
}
