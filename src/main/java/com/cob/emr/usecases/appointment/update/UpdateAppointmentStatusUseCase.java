package com.cob.emr.usecases.appointment.update;

import com.cob.emr.entity.appointment.Appointment;
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
    public void update(SchedulerAppointment schedulerAppointment) {
        appointmentRepository.updateAppointmentStatus(schedulerAppointment.getAppointmentStatus(), schedulerAppointment.getId());
        deAttachFromSeries(schedulerAppointment.getId(), schedulerAppointment.getSeriesId());
    }

    private void deAttachFromSeries(Long appointmentId, Integer seriesId) {
        if (appointmentId != seriesId.longValue()) {
            Appointment appointment = appointmentRepository.findById(appointmentId)
                    .orElseThrow(() -> new IllegalArgumentException("Appointment is not found id : " + appointmentId));
            appointment.setSeriesId(appointmentId.intValue());
            appointmentRepository.save(appointment);
        }
    }
}
