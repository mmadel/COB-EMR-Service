package com.cob.emr.usecases.appointment;

import com.cob.emr.repositories.appointment.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class DeleteAppointmentUseCase {
    @Autowired
    AppointmentRepository appointmentRepository;

    @Transactional
    public void delete(Long appointmentId, Long clinicId) {
        appointmentRepository.findByIdAndClinic_Id(appointmentId, clinicId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found for the given clinic"));
        appointmentRepository.deleteById(appointmentId);
    }
}
