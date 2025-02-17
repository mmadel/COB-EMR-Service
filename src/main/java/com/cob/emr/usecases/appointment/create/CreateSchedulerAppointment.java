package com.cob.emr.usecases.appointment.create;

import com.cob.emr.entity.appointment.Appointment;
import com.cob.emr.model.appointment.SchedulerAppointment;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.StreamSupport;

public abstract class CreateSchedulerAppointment<T> {
    @Transactional
    public T create(SchedulerAppointment appointment) {
        List<SchedulerAppointment> appointments = getAppointmentsRepetition(appointment);
        Iterable<Appointment> persistedAppointments = persistAppointments(appointments);
        assignAppointmentsToCalendar(persistedAppointments, appointment.getCalendarId());
        assignAppointmentsSeriesId(appointment.getSeriesId(), StreamSupport.stream(persistedAppointments.spliterator(), false).toArray(Appointment[]::new));
        return buildRenderedAppointments(persistedAppointments);
    }

    protected abstract List<SchedulerAppointment> getAppointmentsRepetition(SchedulerAppointment appointment);

    protected abstract Iterable<Appointment> persistAppointments(List<SchedulerAppointment> appointments);

    protected abstract void assignAppointmentsToCalendar(Iterable<Appointment> createdAppointments, Long calendarId);

    protected abstract void assignAppointmentsSeriesId(Integer seriesId, Appointment appointments[]);

    protected abstract T buildRenderedAppointments(Iterable<Appointment> createdAppointments);
}
