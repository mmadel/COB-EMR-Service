package com.cob.emr.usecases.appointment.create.repetition;

import com.cob.emr.model.appointment.AppointmentModel;
import com.cob.emr.usecases.appointment.util.RepeatedAppointmentDatesConstructor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DailyAppointmentRepetition implements AppointmentRepetition {
    @Override
    public List<AppointmentModel> repeat(AppointmentModel model) {
        List<AppointmentModel> appointments = new ArrayList<>();
        List<Long> dates = new ArrayList<>();
        createAppointmentsDates(model.getAppointmentRepeat().getDaily().getEvery()
                , model.getAppointmentRepeat().getDaily().getStart()
                , model.getAppointmentRepeat().getDaily().getEnd()
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

    private void createAppointmentsDates(int repeat, long startBoundary, long endBoundary, List<Long> appointmentDates) {
        if (appointmentDates.isEmpty())
            appointmentDates.add(startBoundary);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(startBoundary));
        cal.add(Calendar.DAY_OF_WEEK, repeat);
        long newDate = cal.getTime().getTime();
        if (newDate > endBoundary)
            return;
        appointmentDates.add(newDate);
        createAppointmentsDates(repeat, newDate, endBoundary, appointmentDates);
    }
}
