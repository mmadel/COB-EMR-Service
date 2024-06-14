package com.cob.emr.usecases.appointment.create.repetition;

import com.cob.emr.model.appointment.repeat.AppointmentRepeat;
import com.cob.emr.model.appointment.repeat.YearlyRepeatAppointment;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class YearlyAppointmentRepetition extends AppointmentRepetition {
    @Override
    protected void createAppointmentsDates(AppointmentRepeat model, List<Long> appointmentDates) {
        YearlyRepeatAppointment yearlyRepeatAppointment = model.getYearly();
        if (appointmentDates.isEmpty())
            appointmentDates.add(yearlyRepeatAppointment.getStart());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(yearlyRepeatAppointment.getStart()));
        calendar.add(Calendar.YEAR, yearlyRepeatAppointment.getEvery());
        long newDate = calendar.getTime().getTime();
        if (newDate > yearlyRepeatAppointment.getEnd())
            return;
        appointmentDates.add(newDate);
        model.getYearly().setStart(newDate);
        createAppointmentsDates(model, appointmentDates);
    }
}
