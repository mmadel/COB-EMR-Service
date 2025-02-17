package com.cob.emr.usecases.appointment.series.converter;

import com.cob.emr.model.appointment.repeat.MonthDay;

public class MonthlyExtraDataConverter {
    public static String convert(MonthDay monthDay) {
        return String.join(",",
                monthDay.getDayName(),
                String.valueOf(monthDay.getDayNumber()),
                String.valueOf(monthDay.getIndex()),
                String.valueOf(monthDay.getDay()));
    }

    public static MonthDay convert(String monthDay) {
        String[] parts = monthDay.split(",");
        MonthDay day = new MonthDay();
        day.setDayName(parts[0]);
        day.setDayNumber(Integer.valueOf(parts[1]));
        day.setIndex(Integer.valueOf(parts[2]));
        day.setDay(Integer.valueOf(parts[3]));
        return day;
    }
}
