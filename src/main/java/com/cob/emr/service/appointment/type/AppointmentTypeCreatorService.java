package com.cob.emr.service.appointment.type;

import com.cob.emr.entity.appointment.AppointmentType;
import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.model.appointment.AppointmentTypeModel;
import com.cob.emr.repositories.appointment.AppointmentTypeRepository;
import com.cob.emr.repositories.clinic.ClinicRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentTypeCreatorService {
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
}
