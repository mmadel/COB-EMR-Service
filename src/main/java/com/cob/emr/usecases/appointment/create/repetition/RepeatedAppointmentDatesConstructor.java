package com.cob.emr.usecases.appointment.create.repetition;

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

    private static long createRepeatedAppointmentDateTime(long originalAppointmentDate, long repeatedAppointmentDate) {
        int[] originalAppointmentTime = getOriginalAppointmentTime(originalAppointmentDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(repeatedAppointmentDate));
        cal.add(Calendar.HOUR,originalAppointmentTime[0]+1);
        cal.add(Calendar.MINUTE,0);
        return cal.getTime().getTime();
    }

    private static int[] getOriginalAppointmentTime(long date) {
        Instant instant = Instant.ofEpochMilli(date);
        LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        return new int[]{localDateTime.getHour(), localDateTime.getMinute()};
    }
}
