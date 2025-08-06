package com.cob.emr.usecases.appointment.create.repetition;

import com.cob.emr.model.appointment.repeat.AppointmentRepetitionConfiguration;
import com.cob.emr.model.appointment.repeat.YearlyRepeatAppointment;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class YearlyAppointmentRepetition extends AppointmentRepetition {
    @Override
    protected void createAppointmentsDates(AppointmentRepetitionConfiguration model, List<Long> appointmentDates) {
        YearlyRepeatAppointment yearlyRepeatAppointment = model.getYearly();
        if (appointmentDates.isEmpty())
            appointmentDates.add(yearlyRepeatAppointment.getStartDate());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(yearlyRepeatAppointment.getStartDate()));
        calendar.add(Calendar.YEAR, yearlyRepeatAppointment.getEvery());
        long newDate = calendar.getTime().getTime();
        if (newDate > yearlyRepeatAppointment.getEndDate())
            return;

        if (checkEqualDate(yearlyRepeatAppointment.getInitDate(), newDate)) {
            appointmentDates.add(newDate);
        } else {
            // case for leap year
            int maxDayOfNextMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            int initDay = getInitDay(yearlyRepeatAppointment.getInitDate());
            if (maxDayOfNextMonth == initDay) {
                appointmentDates.add(changeNewDate(newDate, maxDayOfNextMonth));
            }
        }
        model.getYearly().setStartDate(newDate);
        createAppointmentsDates(model, appointmentDates);
    }

    private int getInitDay(Long initDate) {
        LocalDate localDate1 = Instant.ofEpochMilli(initDate)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return localDate1.getDayOfMonth();
    }

    private long changeNewDate(long newDate, int newDayOfMonth) {
        LocalDate date = Instant.ofEpochMilli(newDate)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        // Modify the day of the month
        LocalDate updatedDate = date.withDayOfMonth(newDayOfMonth);

        // Convert the updated LocalDate back to milliseconds
        return updatedDate
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
    }

    private boolean checkEqualDate(Long startDate, Long newDate) {
        LocalDate localDate1 = Instant.ofEpochMilli(startDate)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate localDate2 = Instant.ofEpochMilli(newDate)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        // Check if day and month are equal
        return localDate1.getDayOfMonth() == localDate2.getDayOfMonth() &&
                localDate1.getMonthValue() == localDate2.getMonthValue();

    }
}
