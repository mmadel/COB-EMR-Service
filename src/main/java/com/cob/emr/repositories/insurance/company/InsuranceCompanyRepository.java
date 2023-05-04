package com.cob.emr.repositories.insurance.company;

import com.cob.emr.entity.insurance.InsuranceCompany;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface InsuranceCompanyRepository extends PagingAndSortingRepository<InsuranceCompany, Long> {
    Optional<List<InsuranceCompany>> findByClinicId(Long id);

    Optional<InsuranceCompany> findByIdAndClinic_Id(Long insuranceCompanyId, Long clinicId);
}
