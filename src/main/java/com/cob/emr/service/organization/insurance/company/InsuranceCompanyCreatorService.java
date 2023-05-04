package com.cob.emr.service.organization.insurance.company;

import com.cob.emr.model.insurancecompnay.InsuranceCompanyModel;

public interface InsuranceCompanyCreatorService {

    InsuranceCompanyModel createOrUpdate(InsuranceCompanyModel model);

    void delete(Long id);
}
