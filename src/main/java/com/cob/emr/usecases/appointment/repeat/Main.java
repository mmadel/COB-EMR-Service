package com.cob.emr.usecases.appointment.repeat;

import com.cob.emr.entity.appointment.Appointment;

import java.time.Duration;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        long nowMillis = Instant.now().toEpochMilli();
        long futureWithin15 = nowMillis + (10 * 60 * 1000); // 10 minutes ahead
        long futureBeyond15 = nowMillis + (20 * 60 * 1000); // 20 minutes ahead
        long pastWithin15 = nowMillis - (10 * 60 * 1000); // 10 minutes ago
        long pastBeyond15 = nowMillis - (20 * 60 * 1000); // 20 minutes ago
        System.out.println(isStatusChangeable(1738682100000L));
    }
    private static Boolean isStatusChangeable(Long date){
        Instant appointmentTime = Instant.ofEpochMilli(date);
        Instant now = Instant.now();

        // Calculate the time difference in minutes
        long minutesDifference = Duration.between(now, appointmentTime).toMinutes();

        // If the appointment is more than 15 minutes in the future, return false
        if (minutesDifference > 15) {
            return false;
        }

        // If the appointment is within 15 minutes in the future or past, return true
        return true;
    }
}
