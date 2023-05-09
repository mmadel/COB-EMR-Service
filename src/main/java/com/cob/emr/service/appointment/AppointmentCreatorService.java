package com.cob.emr.service.appointment;

import com.cob.emr.entity.appointment.AppointmentType;
import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.model.appointment.AppointmentTypeModel;
import com.cob.emr.repositories.appointment.AppointmentTypeRepository;
import com.cob.emr.repositories.clinic.ClinicRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentCreatorService {

    @Autowired
    AppointmentTypeRepository appointmentTypeRepository;
    @Autowired
    ClinicRepository clinicRepository;
    @Autowired
    ModelMapper mapper;

    public AppointmentTypeModel createOrUpdate(AppointmentTypeModel model) {
        Clinic clinic = clinicRepository.findById(model.getClinicId())
                .orElseThrow(() -> new IllegalArgumentException("Clinic not found : " + model.getClinicId()));
        AppointmentType toBeCreated = mapper.map(model, AppointmentType.class);
        toBeCreated.setClinic(clinic);
        Long createdEntityId = appointmentTypeRepository.save(toBeCreated).getId();
        model.setId(createdEntityId);
        return model;
    }

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
