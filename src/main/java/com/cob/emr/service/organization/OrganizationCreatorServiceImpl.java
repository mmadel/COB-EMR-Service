package com.cob.emr.service.organization;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.organization.Organization;
import com.cob.emr.model.organization.OrganizationModel;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.organization.OrganizationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrganizationCreatorServiceImpl implements OrganizationCreatorService {
    @Autowired
    OrganizationRepository repository;

    @Autowired
    ClinicRepository clinicRepository;
    @Autowired
    ModelMapper mapper;

    @Override
    public OrganizationModel createOrUpdate(OrganizationModel model) {
        Organization toBeCreated = mapper.map(model, Organization.class);
        toBeCreated.assignClinicsToOrganization();
        Organization created = repository.save(toBeCreated);
        return mapper.map(created, OrganizationModel.class);
    }

    @Override
    public void delete(Long organizationId) {
        Organization toBeDeleted = repository
                .findById(organizationId)
                .orElseThrow(() -> new IllegalArgumentException(organizationId.toString()));

        clinicRepository.deleteAllById(
                toBeDeleted.getClinics()
                        .stream()
                        .map(Clinic::getId)
                        .collect(Collectors.toList()));
        repository.delete(toBeDeleted);
    }
}
