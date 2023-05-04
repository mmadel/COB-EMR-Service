package com.cob.emr.service.insurance.company.creator;

import com.cob.emr.model.insurancecompnay.InsuranceCompanyModel;

public interface InsuranceCompanyCreatorService {

    InsuranceCompanyModel createOrUpdate(InsuranceCompanyModel model);

    void delete(Long id);
}
