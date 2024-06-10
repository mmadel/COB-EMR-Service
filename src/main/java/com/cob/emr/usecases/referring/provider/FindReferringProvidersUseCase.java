package com.cob.emr.usecases.referring.provider;

import com.cob.emr.model.referring.provider.ReferringProviderModel;
import com.cob.emr.repositories.referring.provider.ReferringProviderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindReferringProvidersUseCase {
    @Autowired
    ReferringProviderRepository referringProviderRepository;
    @Autowired
    ModelMapper mapper;

    public List<ReferringProviderModel> find(Long organizationId) {
        return referringProviderRepository.findByOrganization_Id(organizationId).get().stream()
                .map(referringProvider -> mapper.map(referringProvider, ReferringProviderModel.class))
                .collect(Collectors.toList());
    }
}
