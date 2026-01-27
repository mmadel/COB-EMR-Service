package com.cob.emr.usecases.calendar;

import com.cob.emr.repositories.calendar.CalendarClinicRepository;
import com.cob.emr.repositories.calendar.CalendarRepository;
import com.cob.emr.repositories.calendar.UserCalendarAttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class DeleteCalendarUseCase {
    @Autowired
    UserCalendarAttachmentRepository userCalendarAttachmentRepository;
    @Autowired
    CalendarClinicRepository calendarClinicRepository;
    @Autowired
    CalendarRepository calendarRepository;

    @Transactional
    public void deleteByClinic(Long clinicId) {
        userCalendarAttachmentRepository.deleteByCalendar_Clinic_Id(clinicId);
        calendarClinicRepository.deleteByClinic_Id(clinicId);
        calendarRepository.deleteByClinic_Id(clinicId);
    }
}
