package com.cob.emr.usecases.clinic;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.model.clinic.ClinicModel;
import com.cob.emr.repositories.clinic.ClinicRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateClinicUseCase {
    @Autowired
    ClinicRepository clinicRepository;
    @Autowired
    ModelMapper mapper;
    public void update(ClinicModel model){
        Clinic clinic = clinicRepository.findById(model.getId()).get();
        clinic.setName(model.getName());
        clinic.setAddress(model.getAddress());
        clinicRepository.save(clinic);
    }
}
