package com.cob.emr.usecases.calendar;

import com.cob.emr.repositories.calendar.CalendarClinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class UnAttachCalendarWithClinicUseCase {
    @Autowired
    CalendarClinicRepository calendarClinicRepository;

    @Transactional
    public void unattached(List<Long> calendarIds, Long clinicId, String uuid) {
        calendarClinicRepository.unAttachCalendar(clinicId, uuid, calendarIds);
    }
}
