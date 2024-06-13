package com.cob.emr.usecases.appointment.create.repetition;

import com.cob.emr.model.appointment.AppointmentModel;
import com.cob.emr.usecases.appointment.util.RepeatedAppointmentDatesConstructor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WeeklyAppointmentRepetition implements AppointmentRepetition {
    @Override
    public List<AppointmentModel> repeat(AppointmentModel model) {
        List<AppointmentModel> appointments = new ArrayList<>();
        List<Long> dates = new ArrayList<>();
        createAppointmentsDates(model.getAppointmentRepeat().getWeekly().getDays()
                , model.getAppointmentRepeat().getWeekly().getEvery()
                , model.getAppointmentRepeat().getWeekly().getStart()
                , model.getAppointmentRepeat().getWeekly().getEnd()
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

    private void createAppointmentsDates(int[] days, int repeat, long startBoundary, long endBoundary, List<Long> appointmentDates) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(startBoundary);
        calendar.add(Calendar.WEEK_OF_MONTH, repeat);
        long newDate = calendar.getTime().getTime();
        if (newDate > endBoundary)
            return;
        if (days != null) {
            for (int i = 0; i < days.length; i++) {
                calendar.set(Calendar.DAY_OF_WEEK, days[i]);
                long day = calendar.getTime().getTime();
                appointmentDates.add(day);
            }
        } else {
            appointmentDates.add(newDate);
        }
        createAppointmentsDates(days, repeat, newDate, endBoundary, appointmentDates);
    }
}
