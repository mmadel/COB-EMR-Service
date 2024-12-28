package com.cob.emr.usecases.appointment.type;

import com.cob.emr.entity.appointment.AppointmentType;
import com.cob.emr.model.appointment.AppointmentTypeModel;
import com.cob.emr.repositories.appointment.AppointmentTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class FindAppointmentTypeUseCase {
    @Autowired
    AppointmentTypeRepository appointmentTypeRepository;
    @Autowired
    ModelMapper mapper;

    public List<AppointmentTypeModel> findAll() {
        List<AppointmentType> list = StreamSupport.stream(appointmentTypeRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return list.stream()
                .map(appointmentType -> mapper.map(appointmentType, AppointmentTypeModel.class))
                .collect(Collectors.toList());

    }
}
