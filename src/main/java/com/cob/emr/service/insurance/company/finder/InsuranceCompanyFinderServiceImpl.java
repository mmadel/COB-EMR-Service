package com.cob.emr.service.insurance.company.finder;

import com.cob.emr.entity.insurance.InsuranceCompany;
import com.cob.emr.model.insurancecompnay.InsuranceCompanyModel;
import com.cob.emr.repositories.insurance.company.InsuranceCompanyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<InsuranceCompanyModel> findAll(Long clinicId) {
        List<InsuranceCompany> entities = repository.findByClinicId(clinicId)
                .orElseThrow(() -> new IllegalArgumentException(clinicId.toString()));
        return entities
                .stream()
                .map(insuranceCompany -> mapper.map(insuranceCompany, InsuranceCompanyModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public InsuranceCompanyModel findById(Long insuranceCompanyId, Long clinicId) {
        InsuranceCompany entity = repository.findByIdAndClinic_Id(insuranceCompanyId, clinicId)
                .orElseThrow(() -> new IllegalArgumentException(insuranceCompanyId.toString() + ',' + clinicId.toString()));
        return mapper.map(entity, InsuranceCompanyModel.class);
    }
}
