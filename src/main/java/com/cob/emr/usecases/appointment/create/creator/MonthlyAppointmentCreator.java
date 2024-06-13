package com.cob.emr.usecases.appointment.create.creator;

import com.cob.emr.usecases.appointment.create.repetition.MonthlyAppointmentRepetition;

public class MonthlyAppointmentCreator extends AppointmentCreator {
    public MonthlyAppointmentCreator() {
        appointmentRepetition = new MonthlyAppointmentRepetition();
    }
}
