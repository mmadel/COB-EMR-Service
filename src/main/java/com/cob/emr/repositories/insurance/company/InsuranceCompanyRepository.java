package com.cob.emr.repositories.insurance.company;

import com.cob.emr.entity.insurance.InsuranceCompany;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface InsuranceCompanyRepository extends PagingAndSortingRepository<InsuranceCompany, Long> {
    Page<InsuranceCompany> findByClinicId(Pageable pageable, Long id);

    Optional<InsuranceCompany> findByIdAndClinic_Id(Long insuranceCompanyId, Long clinicId);

    List<InsuranceCompany> findByClinicId(Long clinicId);
}
