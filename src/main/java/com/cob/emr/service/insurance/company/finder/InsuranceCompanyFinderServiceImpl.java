package com.cob.emr.service.insurance.company.finder;

import com.cob.emr.entity.insurance.InsuranceCompany;
import com.cob.emr.model.insurancecompnay.InsuranceCompanyModel;
import com.cob.emr.model.response.InsuranceCompanyResponse;
import com.cob.emr.repositories.insurance.company.InsuranceCompanyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InsuranceCompanyFinderServiceImpl implements InsuranceCompanyFinderService {

    @Autowired
    InsuranceCompanyRepository repository;
    @Autowired
    ModelMapper mapper;

    @Override
    public InsuranceCompanyResponse findAll(Pageable pageable, Long clinicId) {
        Page<InsuranceCompany> pages = repository.findByClinicId(pageable, clinicId);
        long total = (pages).getTotalElements();
        List<InsuranceCompanyModel> records = pages.stream().map(insuranceCompany -> mapper.map(insuranceCompany, InsuranceCompanyModel.class))
                .collect(Collectors.toList());
        return InsuranceCompanyResponse.builder()
                .number_of_records(records.size())
                .number_of_matching_records((int) total)
                .records(records)
                .build();
    }

    @Override
    public InsuranceCompanyModel findById(Long insuranceCompanyId, Long clinicId) {
        InsuranceCompany entity = repository.findByIdAndClinic_Id(insuranceCompanyId, clinicId)
                .orElseThrow(() -> new IllegalArgumentException(insuranceCompanyId.toString() + ',' + clinicId.toString()));
        return mapper.map(entity, InsuranceCompanyModel.class);
    }

    @Override
    public List<InsuranceCompanyModel> findAllWithoutPagination(Long clinicId) {
        return repository.findByClinicId(clinicId).stream()
                .map(insuranceCompany -> mapper.map(insuranceCompany, InsuranceCompanyModel.class))
                .collect(Collectors.toList());
    }
}
