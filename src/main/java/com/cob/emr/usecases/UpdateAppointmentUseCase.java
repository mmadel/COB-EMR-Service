package com.cob.emr.usecases;

import com.cob.emr.model.appointment.AppointmentModel;
import com.cob.emr.usecases.appointment.PersistAppointmentUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateAppointmentUseCase {
    @Autowired
    PersistAppointmentUseCase persistAppointmentUseCase;

    public void update(AppointmentModel model) {
        persistAppointmentUseCase.persist(model, true);
    }
}
