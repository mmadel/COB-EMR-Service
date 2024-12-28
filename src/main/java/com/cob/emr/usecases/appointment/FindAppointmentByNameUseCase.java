package com.cob.emr.usecases.appointment;

import com.cob.emr.entity.appointment.AppointmentType;
import com.cob.emr.model.appointment.AppointmentTypeModel;
import com.cob.emr.repositories.appointment.AppointmentTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FindAppointmentByNameUseCase {
    @Autowired
    AppointmentTypeRepository appointmentTypeRepository;
    @Autowired
    ModelMapper mapper;

    public AppointmentTypeModel find(String name, Long clinicId) {
        AppointmentType appointmentType = null;
//        AppointmentType appointmentType = appointmentTypeRepository.findByNameAndClinic_Id(name, clinicId).get();
        return mapper.map(appointmentType, AppointmentTypeModel.class);
    }
}
