package com.cob.emr.service.organization;

import com.cob.emr.entity.organization.Organization;
import com.cob.emr.model.organization.OrganizationModel;
import com.cob.emr.repositories.organization.OrganizationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class OrganizationCreatorServiceImpl implements OrganizationCreatorService {
    @Autowired
    OrganizationRepository repository;

    @Autowired
    ModelMapper mapper;

    @Override
    public OrganizationModel create(OrganizationModel model) {
        Organization toBeCreated = mapper.map(model, Organization.class);
        toBeCreated.assignClinicsToOrganization();
        Organization created = repository.save(toBeCreated);
        return mapper.map(created, OrganizationModel.class);
    }
}
