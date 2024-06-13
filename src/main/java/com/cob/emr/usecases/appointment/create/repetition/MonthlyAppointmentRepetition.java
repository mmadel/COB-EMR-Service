package com.cob.emr.usecases.appointment.create.repetition;

import com.cob.emr.model.appointment.AppointmentModel;
import com.cob.emr.usecases.appointment.util.RepeatedAppointmentDatesConstructor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MonthlyAppointmentRepetition implements AppointmentRepetition {
    @Override
    public List<AppointmentModel> repeat(AppointmentModel model) {
        List<AppointmentModel> appointments = new ArrayList<>();
        List<Long> dates = new ArrayList<>();
        createAppointmentsDates(model.getAppointmentRepeat().getMonthly().getEvery()
                , model.getAppointmentRepeat().getMonthly().getStart()
                , model.getAppointmentRepeat().getMonthly().getEnd()
                , model.getAppointmentRepeat().getMonthly().getDayInMonth()[0]
                , dates);
        dates.stream().map(date -> RepeatedAppointmentDatesConstructor.construct(model.getStartDate()
                , model.getEndDate()
                , date)).forEach(repeatedDate -> {
            AppointmentModel repeatedAppointment = AppointmentCloner.clone(model);
            repeatedAppointment.setStartDate(repeatedDate[0]);
            repeatedAppointment.setEndDate(repeatedDate[1]);
            appointments.add(repeatedAppointment);
        });
        return appointments;
    }

    private void createAppointmentsDates(int repeat, long startBoundary, long endBoundary, int day, List<Long> appointmentDates) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(startBoundary);
        int dayDate = 0;
        if (appointmentDates.isEmpty()) {
            if (day < 7)
                dayDate = RepeatedAppointmentDatesConstructor.getFirstDayInMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), day);
            if (day > 7 && day <= 31)
                dayDate = day;
            if (day > 31)
                dayDate = RepeatedAppointmentDatesConstructor.getDayInMonth(day);
            calendar.set(Calendar.DAY_OF_MONTH, dayDate);
            long appointmentTime = calendar.getTime().getTime();
            if (appointmentTime> startBoundary )
                appointmentDates.add(calendar.getTime().getTime());
        }
        calendar.add(Calendar.MONTH, repeat);
        dayDate = 0;
        if (day < 7)
            dayDate = RepeatedAppointmentDatesConstructor.getFirstDayInMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), day);
        if (day > 7 && day <= 31)
            dayDate = day;
        if (day > 31)
            dayDate = RepeatedAppointmentDatesConstructor.getDayInMonth(day);
        calendar.set(Calendar.DAY_OF_MONTH, dayDate);
        long newDate = calendar.getTime().getTime();
        if (newDate >= endBoundary)
            return;
        appointmentDates.add(newDate);
        createAppointmentsDates(repeat, newDate, endBoundary, day, appointmentDates);
    }
}
