package com.cob.emr.usecases.appointment.cancelnoshow;

import com.cob.emr.entity.appointment.AppointmentCancelNoShowReason;
import com.cob.emr.model.appointment.AppointmentCancelNoShowReasonModel;
import com.cob.emr.repositories.appointment.AppointmentCancelNoShowReasonRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FindAppointmentCancelNoShowUseCase {
    @Autowired
    AppointmentCancelNoShowReasonRepository appointmentCancelNoShowReasonRepository;
    @Autowired
    ModelMapper mapper;

    public AppointmentCancelNoShowReasonModel find(Long appointmentId) {
        AppointmentCancelNoShowReason appointmentCancelNoShowReason = appointmentCancelNoShowReasonRepository.findByAppointment_Id(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("appointment is not found"));
        AppointmentCancelNoShowReasonModel model = mapper.map(appointmentCancelNoShowReason, AppointmentCancelNoShowReasonModel.class);
        model.setStatus(appointmentCancelNoShowReason.getAppointment().getAppointmentStatus());
        return model;
    }
}
