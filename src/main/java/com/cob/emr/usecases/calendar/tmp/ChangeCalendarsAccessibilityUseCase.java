package com.cob.emr.usecases.calendar.tmp;

import com.cob.emr.entity.calendar.CalendarEntity;
import com.cob.emr.model.calendar.CalendarUpdateAttributeModel;
import com.cob.emr.repositories.calendar.CalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChangeCalendarsAccessibilityUseCase {
    @Autowired
    CalendarRepository calendarRepository;

    public void change(CalendarUpdateAttributeModel model) {
        model.getCalendarAccessibilityAttributes().forEach(calendarAttribute -> {
            CalendarEntity calendarEntity = calendarRepository.findById(calendarAttribute.getCalendarId())
                    .orElseThrow(() -> new IllegalArgumentException("Calendar not found during change attribute values"));
            calendarEntity.setIsPublic(calendarAttribute.getIsPublic());
            calendarRepository.save(calendarEntity);
        });
    }
}
