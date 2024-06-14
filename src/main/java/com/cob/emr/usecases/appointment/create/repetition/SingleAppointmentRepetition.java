package com.cob.emr.usecases.appointment.create.repetition;

import com.cob.emr.model.appointment.AppointmentModel;
import com.cob.emr.model.appointment.repeat.AppointmentRepeat;

import java.util.ArrayList;
import java.util.List;

public class SingleAppointmentRepetition extends AppointmentRepetition {

    @Override
    public List<AppointmentModel> repeat(AppointmentModel model) {
        List<AppointmentModel> appointments = new ArrayList<>();
        appointments.add(model);
        return appointments;
    }

    @Override
    protected void createAppointmentsDates(AppointmentRepeat appointmentRepeat, List<Long> appointmentDates) {

    }
}
