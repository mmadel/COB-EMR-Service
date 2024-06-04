package com.cob.emr.repositories.insurance.company;

import com.cob.emr.entity.insurance.InsuranceCompany;
import com.cob.emr.entity.organization.Organization;
import com.cob.emr.entity.patient.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface InsuranceCompanyRepository extends PagingAndSortingRepository<InsuranceCompany, Long> {
    void deleteByOrganization(Organization organization);

    Page<InsuranceCompany> findByOrganization_Id(Pageable pageable, Long id);

    List<InsuranceCompany> findByOrganization_Id(Long id);
}
