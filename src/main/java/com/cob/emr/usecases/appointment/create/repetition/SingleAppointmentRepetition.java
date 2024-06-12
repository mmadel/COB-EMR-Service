package com.cob.emr.usecases.appointment.create.repetition;

import com.cob.emr.model.appointment.AppointmentModel;

import java.util.ArrayList;
import java.util.List;

public class SingleAppointmentRepetition implements AppointmentRepetition {

    @Override
    public List<AppointmentModel> repeat(AppointmentModel model) {
        List<AppointmentModel> appointments = new ArrayList<>();
        appointments.add(model);
        return appointments;
    }
}
