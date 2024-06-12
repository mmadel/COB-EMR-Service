package com.cob.emr.usecases.appointment.create.creator;

import com.cob.emr.model.appointment.AppointmentModel;
import com.cob.emr.usecases.appointment.create.repetition.AppointmentRepetition;

import java.util.List;

public abstract class AppointmentCreator {
    protected AppointmentRepetition appointmentRepetition;
    public List<AppointmentModel> appointments;

    public abstract void create(AppointmentModel model);
}
