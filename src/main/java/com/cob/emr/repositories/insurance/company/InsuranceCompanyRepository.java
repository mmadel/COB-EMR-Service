package com.cob.emr.repositories.insurance.company;

import com.cob.emr.entity.insurance.InsuranceCompany;
import com.cob.emr.entity.organization.Organization;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InsuranceCompanyRepository extends PagingAndSortingRepository<InsuranceCompany, Long> {
    void deleteByOrganization(Organization organization);
}
