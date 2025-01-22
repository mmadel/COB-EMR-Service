package com.cob.emr.usecases.appointment.repeat;

import com.cob.emr.model.role.Role;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Instant instant = Instant.ofEpochMilli(1737439200000L);
        LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        System.out.println(localDateTime.getHour() + " " + localDateTime.getMinute());
    }
}
