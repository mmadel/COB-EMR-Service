package com.cob.emr.usecases.appointment.type;

import com.cob.emr.entity.appointment.AppointmentType;
import com.cob.emr.model.appointment.AppointmentTypeModel;
import com.cob.emr.repositories.appointment.AppointmentTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FindAppointmentTypeByIdUSeCase {
    @Autowired
    AppointmentTypeRepository appointmentTypeRepository;
    @Autowired
    ModelMapper mapper;

    public AppointmentTypeModel find(Long id) {
        AppointmentType appointmentType = appointmentTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Appointment Type not found : id " + id));
        return mapper.map(appointmentType, AppointmentTypeModel.class);
    }
}
