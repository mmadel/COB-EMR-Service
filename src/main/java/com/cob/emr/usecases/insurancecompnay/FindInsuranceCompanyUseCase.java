package com.cob.emr.usecases.insurancecompnay;

import com.cob.emr.entity.insurance.InsuranceCompany;
import com.cob.emr.entity.patient.Patient;
import com.cob.emr.model.insurancecompnay.InsuranceCompanyModel;
import com.cob.emr.model.patient.PatientModel;
import com.cob.emr.model.response.InsuranceCompanyResponse;
import com.cob.emr.model.response.PatientResponse;
import com.cob.emr.repositories.insurance.company.InsuranceCompanyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindInsuranceCompanyUseCase {
    @Autowired
    ModelMapper mapper;
    @Autowired
    InsuranceCompanyRepository repository;

    public InsuranceCompanyResponse find(Pageable paging, Long organizationId) {
        Page<InsuranceCompany> pages = repository.findByOrganization_Id(paging, organizationId);
        long total = (pages).getTotalElements();
        List<InsuranceCompanyModel> records = pages.stream().map(insuranceCompany -> mapper.map(insuranceCompany, InsuranceCompanyModel.class))
                .collect(Collectors.toList());
        return InsuranceCompanyResponse.builder()
                .number_of_records(records.size())
                .number_of_matching_records((int) total)
                .records(records)
                .build();
    }
}
