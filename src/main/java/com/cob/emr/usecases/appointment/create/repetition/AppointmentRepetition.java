package com.cob.emr.usecases.appointment.create.repetition;

import com.cob.emr.model.appointment.AppointmentModel;
import com.cob.emr.model.appointment.repeat.AppointmentRepeat;
import com.cob.emr.usecases.appointment.util.RepeatedAppointmentDatesConstructor;

import java.util.ArrayList;
import java.util.List;

public abstract class AppointmentRepetition {
    public List<AppointmentModel> repeat(AppointmentModel model) {
        List<AppointmentModel> appointments = new ArrayList<>();
        List<Long> dates = new ArrayList<>();
        createAppointmentsDates(model.getAppointmentRepeat()
                , dates);
        dates.stream().map(date -> RepeatedAppointmentDatesConstructor.construct(model.getStartDate()
                , model.getEndDate()
                , date)).forEach(repeatedDate -> {
            AppointmentModel repeatedAppointment = AppointmentCloner.clone(model);
            repeatedAppointment.setStartDate(repeatedDate[0]);
            repeatedAppointment.setEndDate(repeatedDate[1]);
            repeatedAppointment.setCalendarId(model.getCalendarId());
            appointments.add(repeatedAppointment);
        });
        return appointments;
    }

    protected abstract void createAppointmentsDates(AppointmentRepeat appointmentRepeat, List<Long> appointmentDates);
}
