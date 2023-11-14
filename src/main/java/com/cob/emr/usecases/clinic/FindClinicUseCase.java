package com.cob.emr.usecases.clinic;

import com.cob.emr.model.clinic.ClinicModel;
import com.cob.emr.repositories.clinic.ClinicRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FindClinicUseCase {
    @Autowired
    ClinicRepository clinicRepository;
    @Autowired
    ModelMapper mapper;

    public ClinicModel find(Long clinicId) {
        return mapper.map(clinicRepository.findById(clinicId).get(), ClinicModel.class);
    }
}
