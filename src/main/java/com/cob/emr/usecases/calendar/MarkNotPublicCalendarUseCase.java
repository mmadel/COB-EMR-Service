package com.cob.emr.usecases.calendar;

import com.cob.emr.repositories.calendar.CalendarClinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MarkNotPublicCalendarUseCase {
    @Autowired
    CalendarClinicRepository calendarClinicRepository;
    public void setNotPublic(List<Long> calendarIds, Long clinicId, String uuid){
        calendarClinicRepository.unAttachCalendar(clinicId, uuid, calendarIds);
    }
}
