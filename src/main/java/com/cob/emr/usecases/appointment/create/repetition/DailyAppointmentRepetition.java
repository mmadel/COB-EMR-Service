package com.cob.emr.usecases.appointment.create.repetition;

import com.cob.emr.model.appointment.repeat.AppointmentRepeat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DailyAppointmentRepetition extends AppointmentRepetition {
    protected void createAppointmentsDates(AppointmentRepeat model, List<Long> appointmentDates) {
        if (appointmentDates.isEmpty())
            appointmentDates.add(model.getDaily().getStart());
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(model.getDaily().getStart());
        cal.add(Calendar.DAY_OF_WEEK, model.getDaily().getEvery());
        long newDate = cal.getTime().getTime();
        if (newDate > model.getDaily().getEnd())
            return;
        appointmentDates.add(newDate);
        model.getDaily().setStart(newDate);
        createAppointmentsDates(model, appointmentDates);
    }
}
