package com.cob.emr.usecases.appointment.create;

import com.cob.emr.entity.appointment.Appointment;
import com.cob.emr.entity.calendar.CalendarEntity;
import com.cob.emr.model.appointment.RenderAppointment;
import com.cob.emr.model.appointment.SchedulerAppointment;
import com.cob.emr.repositories.appointment.AppointmentRepository;
import com.cob.emr.repositories.calendar.CalendarRepository;
import com.cob.emr.usecases.appointment.ConstructRenderAppointmentUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Qualifier("CreateSingleSchedulerAppointment")
public class CreateSingleSchedulerAppointmentUseCase extends CreateSchedulerAppointment<RenderAppointment> {
    @Autowired
    FetchAppointmentsSchedulerLookupUseCase schedulerAppointmentsLookup;
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    ConstructRenderAppointmentUseCase constructRenderAppointmentUseCase;
    @Autowired
    CreateAppointmentEntityUseCase createAppointmentEntityUseCase;
    @Autowired
    CalendarRepository calendarRepository;

    @Override
    protected List<SchedulerAppointment> getAppointmentsRepetition(SchedulerAppointment appointment) {
        List<SchedulerAppointment> appointments = new ArrayList<>();
        appointments.add(appointment);
        return appointments;
    }

    @Override
    protected Iterable<Appointment> persistAppointments(List<SchedulerAppointment> appointments) {
        SchedulerAppointment appointment = appointments.stream().findFirst().get();
        fetchAppointmentLookups(appointments.stream().findFirst().get());
        Appointment toBecreatedAppointment = createAppointmentEntityUseCase.create(appointment);
        appointmentRepository.save(toBecreatedAppointment);
        List<Appointment> dummyList = new ArrayList<>();
        dummyList.add(toBecreatedAppointment);
        return dummyList;
    }

    @Override
    protected void assignAppointmentsToCalendar(Iterable<Appointment> createdAppointments, Long calendarId) {
        Appointment appointment = StreamSupport.stream(createdAppointments.spliterator(), false)
                .collect(Collectors.toList())
                .stream().findFirst()
                .get();
        CalendarEntity calendar = calendarRepository.findById(calendarId)
                .orElseThrow(() -> new IllegalArgumentException("Calendar not found"));
        calendar.addAppointment(appointment);
        calendarRepository.save(calendar);
    }

    @Override
    protected void assignAppointmentsSeriesId(Integer seriesId, Appointment[] appointments) {
        appointments[0].setSeriesId(appointments[0].getId().intValue());
        appointmentRepository.save(appointments[0]);
    }

    @Override
    protected RenderAppointment buildRenderedAppointments(Iterable<Appointment> createdAppointments) {
        Appointment appointment = StreamSupport.stream(createdAppointments.spliterator(), false)
                .collect(Collectors.toList())
                .stream().findFirst()
                .get();
        return constructRenderAppointmentUseCase.construct(appointment);
    }


    private void fetchAppointmentLookups(SchedulerAppointment appointment) {
        List<SchedulerAppointment> appointments = new ArrayList<>();
        appointments.add(appointment);
        schedulerAppointmentsLookup.fetch(appointments);
        createAppointmentEntityUseCase.setAppointmentLookups(schedulerAppointmentsLookup.clinicsLookups
                , schedulerAppointmentsLookup.patientsLookups
                , schedulerAppointmentsLookup.patientCaseLookups
                , schedulerAppointmentsLookup.appointmentTypeLookups);
    }
}
