package com.cob.emr.service.insurance.company;

import com.cob.emr.entity.insurance.InsuranceCompany;
import com.cob.emr.model.insurancecompnay.InsuranceCompanyModel;
import com.cob.emr.repositories.insurance.company.InsuranceCompanyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class InsuranceCompanyCreatorServiceImpl implements InsuranceCompanyCreatorService {

    @Autowired
    InsuranceCompanyRepository repository;
    @Autowired
    ModelMapper mapper;

    @Override
    public InsuranceCompanyModel createOrUpdate(InsuranceCompanyModel model) {
        InsuranceCompany toBeCreated = mapper.map(model, InsuranceCompany.class);
        InsuranceCompany created = repository.save(toBeCreated);
        return mapper.map(created, InsuranceCompanyModel.class);
    }

    @Override
    public void delete(Long id) {
        InsuranceCompany toBeDeleted = repository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException(id.toString()));
        repository.delete(toBeDeleted);
    }
}
