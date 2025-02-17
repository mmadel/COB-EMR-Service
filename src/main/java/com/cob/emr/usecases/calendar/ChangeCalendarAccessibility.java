package com.cob.emr.usecases.calendar;

import com.cob.emr.entity.calendar.CalendarClinicEntity;
import com.cob.emr.entity.calendar.CalendarEntity;
import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.repositories.calendar.CalendarClinicRepository;
import com.cob.emr.repositories.calendar.CalendarRepository;
import com.cob.emr.repositories.clinic.ClinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class ChangeCalendarAccessibility {
    @Autowired
    CalendarRepository calendarRepository;

    @Transactional
    public void change(List<Long> calendarId, Long clinicId, String uuid, boolean isPublic) {
        List<CalendarClinicEntity> calendarClinicEntities = new ArrayList<>();
        List<CalendarEntity> calendarEntities = calendarRepository.findAllById(calendarId);
        calendarEntities.forEach(calendarEntity -> {
            if (isPublic)
                calendarEntity.setIsPublic(true);
            else
                calendarEntity.setIsPublic(false);
        });
        calendarRepository.saveAll(calendarEntities);

    }
}
