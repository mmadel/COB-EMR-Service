package com.cob.emr.usecases.appointment.create.repetition;

import com.cob.emr.model.appointment.repeat.AppointmentRepetitionConfiguration;

import java.util.Calendar;
import java.util.List;

public class DailyAppointmentRepetition extends AppointmentRepetition {
    protected void createAppointmentsDates(AppointmentRepetitionConfiguration model, List<Long> appointmentDates) {
        if (appointmentDates.isEmpty())
            appointmentDates.add(model.getDaily().getStartDate());
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(model.getDaily().getStartDate());
        cal.add(Calendar.DAY_OF_WEEK, model.getDaily().getEvery());
        long newDate = cal.getTime().getTime();
        if (newDate > model.getDaily().getEndDate())
            return;
        appointmentDates.add(newDate);
        model.getDaily().setStartDate(newDate);
        createAppointmentsDates(model, appointmentDates);
    }
}
