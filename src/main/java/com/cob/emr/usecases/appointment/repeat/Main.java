package com.cob.emr.usecases.appointment.repeat;

import com.cob.emr.model.role.Role;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class Main {
    public static void main(String[] args) {
//        Calendar cacheCalendar = Calendar.getInstance();
//        cacheCalendar.set(Calendar.DAY_OF_MONTH, 2);
//        cacheCalendar.set(Calendar.MONTH, 6);
//        cacheCalendar.set(Calendar.YEAR, 2024);
//        System.out.println(cacheCalendar.get(Calendar.DATE));
        String x = "1320958800000";

        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        long milliSeconds= Long.parseLong(x);
        System.out.println(milliSeconds);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        System.out.println(formatter.format(calendar.getTime()));
    }
}
