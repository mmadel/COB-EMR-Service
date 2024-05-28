package com.cob.emr.usecases.clinic;

import com.cob.emr.model.clinic.ClinicModel;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.security.ClinicalEMRUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindClinicsForUserUseCase {

    @Autowired
    ModelMapper mapper;

    @Autowired
    ClinicRepository clinicRepository;
    @Autowired
    ClinicalEMRUserRepository clinicalEMRUserRepository;


    public List<ClinicModel> find(String userId) {
        return clinicalEMRUserRepository.findUserClinicsByUUID(userId)
                .stream()
                .map(clinic -> mapper.map(clinic, ClinicModel.class))
                .collect(Collectors.toList());
    }
}
