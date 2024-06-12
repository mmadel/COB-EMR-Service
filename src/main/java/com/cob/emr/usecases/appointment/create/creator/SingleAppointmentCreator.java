package com.cob.emr.usecases.appointment.create.creator;

import com.cob.emr.model.appointment.AppointmentModel;

import java.util.ArrayList;
import java.util.List;

public class SingleAppointmentCreator extends AppointmentCreator {

    @Override
    public void create(AppointmentModel model) {
        List<AppointmentModel> appointmentModels = new ArrayList<>();
        appointmentModels.add(model);
        appointments = appointmentModels;
    }
}
