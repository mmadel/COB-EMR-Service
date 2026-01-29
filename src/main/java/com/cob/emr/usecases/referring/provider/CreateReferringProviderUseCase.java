package com.cob.emr.usecases.referring.provider;

import com.cob.emr.entity.organization.Organization;
import com.cob.emr.entity.referring.provider.ReferringProvider;
import com.cob.emr.model.referring.provider.ReferringProviderModel;
import com.cob.emr.repositories.organization.OrganizationRepository;
import com.cob.emr.repositories.referring.provider.ReferringProviderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateReferringProviderUseCase {
    @Autowired
    ModelMapper mapper;
    @Autowired
    ReferringProviderRepository referringProviderRepository;
    @Autowired
    OrganizationRepository organizationRepository;
    public void create(ReferringProviderModel model){
        if(referringProviderRepository.existsByNpi(model.getNpi()))
            throw new IllegalArgumentException("Referring provider with NPI " + model.getNpi() + " already exists");
        ReferringProvider referringProvider = mapper.map(model, ReferringProvider.class);
        Organization organization =organizationRepository.findById(model.getOrganizationId()).get();
        referringProvider.setOrganization(organization);
        referringProviderRepository.save(referringProvider);
    }
}
