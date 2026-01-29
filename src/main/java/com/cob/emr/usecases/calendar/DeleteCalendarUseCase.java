package com.cob.emr.usecases.calendar;

import com.cob.emr.exception.business.CalendarException;
import com.cob.emr.repositories.appointment.AppointmentRepository;
import com.cob.emr.repositories.calendar.CalendarClinicRepository;
import com.cob.emr.repositories.calendar.CalendarRepository;
import com.cob.emr.repositories.calendar.UserCalendarAttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @Autowired
    AppointmentRepository appointmentRepository;

    @Transactional
    public void delete(Long calendarId) throws CalendarException {
        if (appointmentRepository.existsByCalendar_Id(calendarId))
            throw new CalendarException(HttpStatus.CONFLICT, CalendarException.CALENDAR_HAS_APPOINTMENTS, new Object[]{calendarId});
        userCalendarAttachmentRepository.deleteByCalendar_Id(calendarId);
        calendarRepository.deleteById(calendarId);
    }

    @Transactional
    public void deleteByClinic(Long clinicId) {
        userCalendarAttachmentRepository.deleteByCalendar_Clinic_Id(clinicId);
        calendarClinicRepository.deleteByClinic_Id(clinicId);
        calendarRepository.deleteByClinic_Id(clinicId);
    }
}
