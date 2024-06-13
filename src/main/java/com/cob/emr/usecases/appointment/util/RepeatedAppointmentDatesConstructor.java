package com.cob.emr.usecases.appointment.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class RepeatedAppointmentDatesConstructor {
    public static long[] construct(long originalStartDate, long originalEndDate, long repeatedAppointmentDate) {
        long start = createRepeatedAppointmentDateTime(originalStartDate, repeatedAppointmentDate);
        long end = createRepeatedAppointmentDateTime(originalEndDate, repeatedAppointmentDate);
        return new long[]{start, end};
    }

    public static int getFirstDayInMonth(int year, int month, int day) {
        Calendar cacheCalendar = Calendar.getInstance();
        cacheCalendar.set(Calendar.DAY_OF_WEEK, day);
        cacheCalendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, 1);
        cacheCalendar.set(Calendar.MONTH, month);
        cacheCalendar.set(Calendar.YEAR, year);
        return cacheCalendar.get(Calendar.DATE);
    }

    public static int getDayInMonth(int day) {
        int changedDay = 0;
        if (day == 32)
            changedDay = 1;
        if (day == 33)
            changedDay = 2;
        if (day == 34)
            changedDay = 3;
        return changedDay;
    }

    private static long createRepeatedAppointmentDateTime(long originalAppointmentDate, long repeatedAppointmentDate) {
        int[] originalAppointmentTime = getOriginalAppointmentTime(originalAppointmentDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(repeatedAppointmentDate));
        cal.add(Calendar.HOUR, originalAppointmentTime[0] + 1);
        cal.add(Calendar.MINUTE, originalAppointmentTime[1]);
        return cal.getTime().getTime();
    }

    private static int[] getOriginalAppointmentTime(long date) {
        Instant instant = Instant.ofEpochMilli(date);
        LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        return new int[]{localDateTime.getHour(), localDateTime.getMinute()};
    }

}
