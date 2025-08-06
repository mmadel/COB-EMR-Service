package com.cob.emr.usecases.appointment.create.repetition;

import com.cob.emr.model.appointment.AppointmentRepetitionType;
import com.cob.emr.model.appointment.SchedulerAppointment;
import com.cob.emr.model.appointment.repeat.AppointmentRepetitionConfiguration;
import com.cob.emr.usecases.appointment.util.RepeatedAppointmentDatesConstructor;

import java.util.ArrayList;
import java.util.List;

public abstract class AppointmentRepetition {
    public List<SchedulerAppointment> createAppointments(SchedulerAppointment model) {
        if (model.getAppointmentRepetitionConfiguration() == null) {
            List<SchedulerAppointment> appointments = new ArrayList<>();
            appointments.add(model);
            return appointments;
        }
        List<SchedulerAppointment> appointments = new ArrayList<>();
        List<Long> dates = new ArrayList<>();
        createAppointmentsDates(model.getAppointmentRepetitionConfiguration()
                , dates);
        dates.stream().map(date -> RepeatedAppointmentDatesConstructor.construct(model.getStartDate()
                , model.getEndDate()
                , date)).forEach(repeatedDate -> {
            SchedulerAppointment repeatedAppointment = AppointmentCloner.clone(model);
            repeatedAppointment.setStartDate(repeatedDate[0]);
            repeatedAppointment.setEndDate(repeatedDate[1]);
            repeatedAppointment.setCalendarId(model.getCalendarId());
            appointments.add(repeatedAppointment);
        });
        return appointments;
    }

    protected abstract void createAppointmentsDates(AppointmentRepetitionConfiguration appointmentRepetitionConfiguration, List<Long> appointmentDates);
}
