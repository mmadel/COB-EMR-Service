package com.cob.emr.usecases.appointment.create.repetition;

import com.cob.emr.model.appointment.repeat.AppointmentRepetitionConfiguration;
import com.cob.emr.model.appointment.repeat.MonthDay;
import com.cob.emr.model.appointment.repeat.MonthlyRepeatAppointment;
import com.cob.emr.usecases.appointment.util.RepeatedAppointmentDatesConstructor;

import java.util.Calendar;
import java.util.List;

public class MonthlyAppointmentRepetition extends AppointmentRepetition {
    @Override
    protected void createAppointmentsDates(AppointmentRepetitionConfiguration model, List<Long> appointmentDates) {
        MonthlyRepeatAppointment monthlyRepeatAppointment = model.getMonthly();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(monthlyRepeatAppointment.getStartDate());
        int dayDate = 0;
        if (appointmentDates.isEmpty()) {
            dayDate = getDay(calendar, monthlyRepeatAppointment.getDays());
            calendar.set(Calendar.DAY_OF_MONTH, dayDate);
            long appointmentTime = calendar.getTime().getTime();
            if (appointmentTime >= monthlyRepeatAppointment.getStartDate())
                appointmentDates.add(calendar.getTime().getTime());
        }
        calendar.add(Calendar.MONTH, monthlyRepeatAppointment.getEvery());
        dayDate = getDay(calendar, monthlyRepeatAppointment.getDays());
        int maxDayOfNextMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, Math.min(dayDate, maxDayOfNextMonth));
        long newDate = calendar.getTime().getTime();
        if (newDate >= monthlyRepeatAppointment.getEndDate())
            return;
        appointmentDates.add(newDate);
        model.getMonthly().setStartDate(newDate);
        createAppointmentsDates(model, appointmentDates);
    }

    private int getDay(Calendar calendar, MonthDay day) {
        // pass day as name like monday
        if (day.getDay() == -1) {
            return RepeatedAppointmentDatesConstructor.getIndexOfDay(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), day.getDayNumber(), day.getIndex());
        }
        //pass day as number 21th
        else {
            return day.getDay();
        }
    }
}
