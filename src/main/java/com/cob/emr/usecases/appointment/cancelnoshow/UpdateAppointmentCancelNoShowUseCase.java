package com.cob.emr.usecases.appointment.cancelnoshow;

import com.cob.emr.entity.appointment.Appointment;
import com.cob.emr.entity.appointment.AppointmentCancelNoShowReason;
import com.cob.emr.model.appointment.AppointmentCancelNoShowReasonModel;
import com.cob.emr.repositories.appointment.AppointmentCancelNoShowReasonRepository;
import com.cob.emr.repositories.appointment.AppointmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
public class UpdateAppointmentCancelNoShowUseCase {
    @Autowired
    AppointmentCancelNoShowReasonRepository repository;
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    ModelMapper mapper;

    @Transactional
    public void update(AppointmentCancelNoShowReasonModel model) {
        Appointment appointment = appointmentRepository.findById(model.getAppointmentId())
                .orElseThrow(() -> new IllegalArgumentException("Appointment with id " + model.getAppointmentId() + " not found "));
        AppointmentCancelNoShowReason entity;
        Optional<AppointmentCancelNoShowReason> optional = repository.findByAppointment_Id(model.getAppointmentId());
        if (optional.isPresent())
            entity = optional.get();
        else
            entity = mapper.map(model, AppointmentCancelNoShowReason.class);
        appointment.setAppointmentStatus(model.getStatus());
        repository.save(entity);
        appointmentRepository.save(appointment);
    }
}
