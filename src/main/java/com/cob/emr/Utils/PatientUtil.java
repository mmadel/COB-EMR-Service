package com.cob.emr.Utils;

import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class PatientUtil {
    public static String constructPatientName(String firstName, String middleName, String lastName) {
        return  Stream.of(firstName, middleName, lastName)
                .filter(x -> x != null && !x.isEmpty())
                .collect(joining(" "));
    }
}
