package com.cob.emr.service.clinic;

import com.cob.emr.model.clinic.ClinicModel;
import com.cob.emr.repositories.clinic.ClinicRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClinicFinderServiceImpl implements ClinicFinderService {

    @Autowired
    ClinicRepository clinicRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public List<ClinicModel> findByIds(List<Long> ids) {
        List<ClinicModel> models = new ArrayList<>();
        clinicRepository.findAllById(ids)
                .forEach(clinic -> models.add(mapper.map(clinic, ClinicModel.class)));
        return models;
    }
}
