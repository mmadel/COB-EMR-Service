package com.cob.emr.usecases.appointment.create.creator;

import com.cob.emr.model.appointment.AppointmentModel;
import com.cob.emr.usecases.appointment.create.repetition.DailyAppointmentRepetition;

public class DailyAppointmentCreator extends AppointmentCreator {
    public DailyAppointmentCreator() {
        appointmentRepetition = new DailyAppointmentRepetition();
    }
}
