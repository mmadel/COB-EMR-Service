package com.cob.emr.usecases.appointment.create.repetition;

import com.cob.emr.model.appointment.repeat.AppointmentRepeat;
import com.cob.emr.model.appointment.repeat.MonthDay;
import com.cob.emr.model.appointment.repeat.MonthlyRepeatAppointment;
import com.cob.emr.usecases.appointment.util.RepeatedAppointmentDatesConstructor;

import java.util.Calendar;
import java.util.List;

public class MonthlyAppointmentRepetition extends AppointmentRepetition {
    @Override
    protected void createAppointmentsDates(AppointmentRepeat model, List<Long> appointmentDates) {
        MonthlyRepeatAppointment monthlyRepeatAppointment = model.getMonthly();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(monthlyRepeatAppointment.getStart());
        int dayDate = 0;
        if (appointmentDates.isEmpty()) {
            int day = monthlyRepeatAppointment.getDayInMonth()[0];
            if (day < 7)
                dayDate = RepeatedAppointmentDatesConstructor.getFirstDayInMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), day);
            if (day > 7 && day <= 31)
                dayDate = day;
            if (day > 31)
                dayDate = RepeatedAppointmentDatesConstructor.getDayInMonth(day);
            calendar.set(Calendar.DAY_OF_MONTH, dayDate);
            long appointmentTime = calendar.getTime().getTime();
            if (appointmentTime > monthlyRepeatAppointment.getStart())
                appointmentDates.add(calendar.getTime().getTime());
        }
        calendar.add(Calendar.MONTH, monthlyRepeatAppointment.getEvery());
        dayDate = 0;
        int day = monthlyRepeatAppointment.getDayInMonth()[0];
        if (day < 7)
            dayDate = RepeatedAppointmentDatesConstructor.getFirstDayInMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), monthlyRepeatAppointment.getDayInMonth()[0]);
        if (day > 7 && day <= 31)
            dayDate = day;
        if (day > 31)
            dayDate = RepeatedAppointmentDatesConstructor.getDayInMonth(day);
        int maxDayOfNextMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, Math.min(dayDate, maxDayOfNextMonth));
        long newDate = calendar.getTime().getTime();
        if (newDate >= monthlyRepeatAppointment.getEnd())
            return;
        appointmentDates.add(newDate);
        model.getMonthly().setStart(newDate);
        createAppointmentsDates(model, appointmentDates);
    }
    private int dd(MonthDay day){
        // return datDate number based on i
    }
}
