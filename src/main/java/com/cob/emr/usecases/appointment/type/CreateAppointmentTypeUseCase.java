package com.cob.emr.usecases.appointment.type;

import com.cob.emr.entity.appointment.AppointmentType;
import com.cob.emr.model.appointment.AppointmentTypeModel;
import com.cob.emr.repositories.appointment.AppointmentTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateAppointmentTypeUseCase {
    @Autowired
    ModelMapper mapper;
    @Autowired
    AppointmentTypeRepository appointmentTypeRepository;
    public Long create(AppointmentTypeModel model){
        AppointmentType appointmentType = mapper.map(model,AppointmentType.class);
        return appointmentTypeRepository.save(appointmentType).getId();
    }
}
