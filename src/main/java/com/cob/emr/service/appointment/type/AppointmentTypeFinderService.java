package com.cob.emr.service.appointment.type;

import com.cob.emr.entity.appointment.AppointmentType;
import com.cob.emr.model.appointment.AppointmentTypeModel;
import com.cob.emr.repositories.appointment.AppointmentTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentTypeFinderService {
    @Autowired
    AppointmentTypeRepository appointmentTypeRepository;

    @Autowired
    ModelMapper mapper;

    public List<AppointmentTypeModel> findAll(Long clinicId) {
        return appointmentTypeRepository.findByClinicId(clinicId)
                .orElseThrow(() -> new IllegalArgumentException("Error when find all appointments Types"))
                .stream()
                .map(appointmentType -> mapper.map(appointmentType, AppointmentTypeModel.class))
                .collect(Collectors.toList());
    }

    public AppointmentTypeModel findById(Long id, Long clinicId) {
        AppointmentType appointmentType = appointmentTypeRepository.findByIdAndClinic_Id(id, clinicId)
                .orElseThrow(() -> new IllegalArgumentException("Error when find all appointments Types"));

        return mapper.map(appointmentType, AppointmentTypeModel.class);
    }
}
