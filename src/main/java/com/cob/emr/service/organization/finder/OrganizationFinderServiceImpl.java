package com.cob.emr.service.organization.finder;

import com.cob.emr.model.organization.OrganizationModel;
import com.cob.emr.repositories.organization.OrganizationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrganizationFinderServiceImpl implements OrganizationFinderService {

    @Autowired
    OrganizationRepository repository;

    @Autowired
    ModelMapper mapper;

    @Override
    public List<OrganizationModel> findAll() {
        List<OrganizationModel> result = new ArrayList<>();
        repository.findAll()
                .forEach(organization ->
                        result.add(mapper.map(organization, OrganizationModel.class)));
        return result;
    }

    @Override
    public OrganizationModel findById(Long id) {
        return mapper
                .map(repository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException(id.toString()))
                        , OrganizationModel.class);


    }
}
