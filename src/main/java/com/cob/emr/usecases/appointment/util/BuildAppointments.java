package com.cob.emr.usecases.appointment.util;


import com.cob.emr.entity.appointment.Appointment;
import com.cob.emr.enums.AppointmentStructure;
import com.cob.emr.model.appointment.AppointmentModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class BuildAppointments {

    @Autowired
    ModelMapper mapper;

    public List<AppointmentModel> build(Iterable<Appointment> createdAppointments) {
        List<Appointment> list = StreamSupport.stream(createdAppointments.spliterator(), false)
                .collect(Collectors.toList());
        return list.stream()
                .map(appointment -> {
                    AppointmentModel appointmentModel = mapper.map(appointment, AppointmentModel.class);
                    appointmentModel.setAppointmentTypeColor(appointment.getAppointmentType().getColor());
                    appointmentModel.setAppointmentFontTypeColor(appointment.getAppointmentType().getFontColor());
                    appointmentModel.setAppointmentStructure(appointment.getPatient() == null ? AppointmentStructure.Block : AppointmentStructure.Full);
                    appointmentModel.setAppointmentType(appointment.getAppointmentType().getName());
                    return appointmentModel;
                }).collect(Collectors.toList());
    }
}
