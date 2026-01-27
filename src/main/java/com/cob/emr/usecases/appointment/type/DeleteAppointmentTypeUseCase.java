package com.cob.emr.usecases.appointment.type;

import com.cob.emr.repositories.appointment.AppointmentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteAppointmentTypeUseCase {
    @Autowired
    AppointmentTypeRepository appointmentTypeRepository;

    public void deleteByClinic(Long clinicId) {

    }
}
