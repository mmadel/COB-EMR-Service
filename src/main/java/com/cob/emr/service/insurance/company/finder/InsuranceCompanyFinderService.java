package com.cob.emr.service.insurance.company.finder;

import com.cob.emr.model.insurancecompnay.InsuranceCompanyModel;

import java.util.List;

public interface InsuranceCompanyFinderService {
    List<InsuranceCompanyModel> findAll(Long clinicId);

    InsuranceCompanyModel findById(Long insuranceCompanyId, Long clinicId);

}
