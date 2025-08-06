package com.cob.emr.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class PatientUtil {
    public static String constructPatientName(String firstName, String middleName, String lastName) {
        return  Stream.of(firstName, middleName, lastName)
                .filter(x -> x != null && !x.isEmpty())
                .collect(joining(" "));
    }

    public static String constructDisplayName(String lastName , String firstName , Long dos){
        return lastName  +", "+firstName +" - " +getPatientDOS(dos);
    }

    private static String getPatientDOS(Long dos){
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dos);
        return formatter.format(calendar.getTime());
    }
}
