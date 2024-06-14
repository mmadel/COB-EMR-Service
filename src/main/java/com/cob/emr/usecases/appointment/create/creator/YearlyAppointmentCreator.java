package com.cob.emr.usecases.appointment.create.creator;

import com.cob.emr.usecases.appointment.create.repetition.YearlyAppointmentRepetition;

public class YearlyAppointmentCreator extends AppointmentCreator {
    public YearlyAppointmentCreator() {
        appointmentRepetition = new YearlyAppointmentRepetition();
    }
}
