package com.cob.emr.usecases.clinic;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.organization.Organization;
import com.cob.emr.model.clinic.ClinicModel;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.organization.OrganizationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class CreateClinicUseCase {
    @Autowired
    ClinicRepository clinicRepository;
    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    ModelMapper mapper;

    @PreAuthorize("hasAnyRole(@roleEvaluator.findAuthorizedRoleForCreation('clinic'))")
    public Long create(ClinicModel model) {
        Organization organization = organizationRepository.findById(model.getOrganizationId()).get();
        Clinic toBeCreated = mapper.map(model, Clinic.class);
        toBeCreated.setOrganization(organization);
        return clinicRepository.save(toBeCreated).getId();
    }
}
