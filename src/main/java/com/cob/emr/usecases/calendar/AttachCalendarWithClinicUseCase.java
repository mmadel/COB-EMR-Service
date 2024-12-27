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
public class AttachCalendarWithClinicUseCase {
    @Autowired
    CalendarRepository calendarRepository;
    @Autowired
    ClinicRepository clinicRepository;
    @Autowired
    CalendarClinicRepository calendarClinicRepository;

    @Transactional
    public void attach(List<Long> calendarId, Long clinicId ,String uuid){
        List<CalendarClinicEntity> calendarClinicEntities = new ArrayList<>();
        List<CalendarEntity> calendarEntities = calendarRepository.findAllById(calendarId);
        Clinic clinic = clinicRepository.findById(clinicId).get();
        calendarEntities.forEach(calendarEntity -> {
            CalendarClinicEntity calendarClinic = new CalendarClinicEntity();
            calendarClinic.setCalendarEntity(calendarEntity);
            calendarClinic.setClinic(clinic);
            calendarClinic.setUserUUID(uuid);
            calendarClinicEntities.add(calendarClinic);
        });
        calendarClinicRepository.saveAll(calendarClinicEntities);
    }
}
