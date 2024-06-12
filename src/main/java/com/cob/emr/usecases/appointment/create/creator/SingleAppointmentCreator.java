package com.cob.emr.usecases.appointment.create.creator;

import com.cob.emr.model.appointment.AppointmentModel;
import com.cob.emr.usecases.appointment.create.repetition.SingleAppointmentRepetition;

public class SingleAppointmentCreator extends AppointmentCreator {
    public SingleAppointmentCreator() {
        appointmentRepetition = new SingleAppointmentRepetition();
    }
    @Override
    public void create(AppointmentModel model) {
        appointments = appointmentRepetition.repeat(model);
    }
}
