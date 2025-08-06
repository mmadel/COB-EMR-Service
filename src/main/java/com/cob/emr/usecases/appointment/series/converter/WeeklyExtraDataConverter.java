package com.cob.emr.usecases.appointment.series.converter;

import java.util.Arrays;
import java.util.stream.Collectors;

public class WeeklyExtraDataConverter {
    public static String convert(int[] days) {
        return Arrays.stream(days)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(","));
    }

    public static int[] convert(String days) {
        return Arrays.stream(days.split(",")) // Split the string into parts
                .mapToInt(Integer::parseInt) // Convert each part to int
                .toArray();
    }
}
