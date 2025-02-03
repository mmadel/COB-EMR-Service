package com.cob.emr.usecases.appointment.create;

import com.cob.emr.entity.appointment.Appointment;
import com.cob.emr.entity.calendar.CalendarEntity;
import com.cob.emr.model.appointment.RenderAppointment;
import com.cob.emr.model.appointment.SchedulerAppointment;
import com.cob.emr.repositories.appointment.AppointmentRepository;
import com.cob.emr.repositories.calendar.CalendarRepository;
import com.cob.emr.usecases.appointment.ConstructRenderAppointmentUseCase;
import com.cob.emr.usecases.appointment.create.repetition.AppointmentRepetition;
import com.cob.emr.usecases.appointment.create.repetition.AppointmentRepetitionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Qualifier("CreateSeriesSchedulerAppointment")
public class CreateSeriesSchedulerAppointmentUseCase extends CreateSchedulerAppointment<List<RenderAppointment>> {
    @Autowired
    FetchAppointmentsSchedulerLookupUseCase schedulerAppointmentsLookup;
    @Autowired
    CreateAppointmentEntityUseCase createAppointmentEntityUseCase;
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    CalendarRepository calendarRepository;
    @Autowired
    ConstructRenderAppointmentUseCase constructRenderAppointmentUseCase;

    @Override
    protected List<SchedulerAppointment> getAppointmentsRepetition(SchedulerAppointment appointment) {
        AppointmentRepetition repetition = AppointmentRepetitionFactory.getInstance(appointment);
        return repetition.createAppointments(appointment);
    }

    @Override
    protected Iterable<Appointment> persistAppointments(List<SchedulerAppointment> appointments) {
        fetchAppointmentLookups(appointments);
        List<Appointment> createdList = new ArrayList<>();
        appointments.forEach(appointment -> {
            createdList.add((createAppointmentEntityUseCase.create(appointment)));
        });
        return appointmentRepository.saveAll(createdList);
    }

    @Override
    protected void assignAppointmentsToCalendar(Iterable<Appointment> createdAppointments, Long calendarId) {
        List<Appointment> list = StreamSupport.stream(createdAppointments.spliterator(), false)
                .collect(Collectors.toList());
        CalendarEntity calendar = calendarRepository.findById(calendarId)
                .orElseThrow(() -> new IllegalArgumentException("Calendar not found"));
        list.forEach(appointment -> {
            calendar.addAppointment(appointment);
        });
        calendarRepository.save(calendar);
    }

    @Override
    protected void assignAppointmentsSeriesId(Integer seriesId, Appointment appointments[]) {
        for (Appointment appointment : appointments) {
            appointment.setSeriesId(seriesId);
        }
        appointmentRepository.saveAll(Arrays.asList(appointments));
    }

    @Override
    protected List<RenderAppointment> buildRenderedAppointments(Iterable<Appointment> createdAppointments) {
        return constructRenderAppointmentUseCase.construct(createdAppointments);
    }


    private void fetchAppointmentLookups(List<SchedulerAppointment> appointments) {
        schedulerAppointmentsLookup.fetch(appointments);
        createAppointmentEntityUseCase.setAppointmentLookups(schedulerAppointmentsLookup.clinicsLookups
                , schedulerAppointmentsLookup.patientsLookups
                , schedulerAppointmentsLookup.patientCaseLookups
                , schedulerAppointmentsLookup.appointmentTypeLookups);
    }
}
