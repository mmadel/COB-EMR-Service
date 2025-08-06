package com.cob.emr.usecases.insurancecompnay;

import com.cob.emr.entity.organization.Organization;
import com.cob.emr.repositories.insurance.company.InsuranceCompanyRepository;
import com.cob.emr.repositories.organization.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteInsuranceCompanyUseCase {
    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    InsuranceCompanyRepository insuranceCompanyRepository;

    public void delete(Long organizationId) {
        Organization organization = organizationRepository.findById(organizationId).get();
        insuranceCompanyRepository.deleteByOrganization(organization);
    }

    public void deleteById(Long insuranceCompanyId) {

    }
}
