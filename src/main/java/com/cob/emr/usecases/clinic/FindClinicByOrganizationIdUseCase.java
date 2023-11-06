package com.cob.emr.usecases.clinic;

import com.cob.emr.model.clinic.ClinicModel;
import com.cob.emr.repositories.clinic.ClinicRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindClinicByOrganizationIdUseCase {
    @Autowired
    ClinicRepository clinicRepository;
    @Autowired
    ModelMapper mapper;
    public List<ClinicModel> find(Long organizationId){
        return clinicRepository.findByOrganization(organizationId)
                .stream().map(clinic -> mapper.map(clinic , ClinicModel.class))
                .collect(Collectors.toList());
    }
}
