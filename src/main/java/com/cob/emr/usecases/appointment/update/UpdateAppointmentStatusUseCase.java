package com.cob.emr.usecases.appointment.update;

import com.cob.emr.model.appointment.SchedulerAppointment;
import com.cob.emr.repositories.appointment.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class UpdateAppointmentStatusUseCase {
    @Autowired
    AppointmentRepository appointmentRepository;
    @Transactional
    public void update(SchedulerAppointment schedulerAppointment){
        appointmentRepository.updateAppointmentStatus(schedulerAppointment.getAppointmentStatus(),schedulerAppointment.getId());
    }
}
