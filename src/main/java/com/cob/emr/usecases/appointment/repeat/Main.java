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
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        System.out.println(calendar.getTime());
        calendar.add(Calendar.MONTH, 1);
        int maxDayOfNextMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, Math.min(30, maxDayOfNextMonth));
        System.out.println(calendar.getTime());
    }
}
