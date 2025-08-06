package com.cob.emr.usecases.referring.provider;

import com.cob.emr.entity.patient.Patient;
import com.cob.emr.entity.referring.provider.ReferringProvider;
import com.cob.emr.model.patient.PatientModel;
import com.cob.emr.model.referring.provider.ReferringProviderModel;
import com.cob.emr.model.response.PatientResponse;
import com.cob.emr.model.response.ReferringProviderResponse;
import com.cob.emr.repositories.referring.provider.ReferringProviderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindReferringProvidersUseCase {
    @Autowired
    ReferringProviderRepository referringProviderRepository;
    @Autowired
    ModelMapper mapper;

    public ReferringProviderResponse find(Pageable paging, Long organizationId) {
        Page<ReferringProvider> pages  = referringProviderRepository.findByOrganization_Id(paging, organizationId);
        long total = (pages).getTotalElements();
        List<ReferringProviderModel> records = pages.stream().map(referringProvider -> mapper.map(referringProvider,ReferringProviderModel.class))
                .collect(Collectors.toList());

        return ReferringProviderResponse.builder()
                .number_of_records(records.size())
                .number_of_matching_records((int) total)
                .records(records)
                .build();
    }
}
