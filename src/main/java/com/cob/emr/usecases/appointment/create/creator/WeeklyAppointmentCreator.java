package com.cob.emr.usecases.appointment.create.creator;

import com.cob.emr.model.appointment.AppointmentModel;
import com.cob.emr.usecases.appointment.create.repetition.WeeklyAppointmentRepetition;

public class WeeklyAppointmentCreator extends AppointmentCreator{
    public WeeklyAppointmentCreator() {
        appointmentRepetition = new WeeklyAppointmentRepetition();
    }

    @Override
    public void create(AppointmentModel model) {
        appointments = appointmentRepetition.repeat(model);
    }
}
