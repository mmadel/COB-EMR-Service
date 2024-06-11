package com.cob.emr.usecases.referring.provider;

import com.cob.emr.entity.referring.provider.ReferringProvider;
import com.cob.emr.model.referring.provider.ReferringProviderModel;
import com.cob.emr.repositories.referring.provider.ReferringProviderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateReferringProviderUseCase {
    @Autowired
    ModelMapper mapper;
    @Autowired
    ReferringProviderRepository referringProviderRepository;
    public void update(ReferringProviderModel model){
        ReferringProvider referringProvider = mapper.map(model,ReferringProvider.class);
        referringProviderRepository.save(referringProvider);
    }
}
