package com.cob.emr.usecases.referring.provider;

import com.cob.emr.repositories.referring.provider.ReferringProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class DeleteReferringProviderUseCase {
    @Autowired
    ReferringProviderRepository referringProviderRepository;

    @Transactional
    public void deleteByOrganization(Long organizationId) {
        referringProviderRepository.deleteByOrganization_Id(organizationId);
    }
}
