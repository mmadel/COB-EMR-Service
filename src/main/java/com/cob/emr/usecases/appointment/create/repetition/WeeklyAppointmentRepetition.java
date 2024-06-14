package com.cob.emr.usecases.appointment.create.repetition;

import com.cob.emr.model.appointment.repeat.AppointmentRepeat;

import java.util.Calendar;
import java.util.List;

public class WeeklyAppointmentRepetition extends AppointmentRepetition {
    @Override
    protected void createAppointmentsDates(AppointmentRepeat model, List<Long> appointmentDates) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(model.getWeekly().getStart());
        calendar.add(Calendar.WEEK_OF_MONTH, model.getWeekly().getEvery());
        long newDate = calendar.getTime().getTime();
        if (newDate > model.getWeekly().getEnd())
            return;
        if (model.getWeekly().getDays() != null) {
            for (int i = 0; i < model.getWeekly().getDays().length; i++) {
                calendar.set(Calendar.DAY_OF_WEEK, model.getWeekly().getDays()[i]);
                long day = calendar.getTime().getTime();
                appointmentDates.add(day);
            }
        } else {
            appointmentDates.add(newDate);
        }
        model.getWeekly().setStart(newDate);
        createAppointmentsDates(model, appointmentDates);
    }
}
