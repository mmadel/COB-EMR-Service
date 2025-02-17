package com.cob.emr.usecases.calendar;

import com.cob.emr.entity.calendar.CalendarEntity;
import com.cob.emr.model.calendar.CalendarModel;
import com.cob.emr.repositories.calendar.CalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateCalendarNameUSeCase {
    @Autowired
    CalendarRepository calendarRepository;
    public void update(CalendarModel model){
        CalendarEntity calendarEntity = calendarRepository.findById(model.getId())
                .orElseThrow(() -> new IllegalArgumentException("Calendar not found"));
        calendarEntity.setName(model.getName());
        calendarRepository.save(calendarEntity);
    }
}
