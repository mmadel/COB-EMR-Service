package com.cob.emr.service.insurance.company.finder;

import com.cob.emr.model.insurancecompnay.InsuranceCompanyModel;
import com.cob.emr.model.response.InsuranceCompanyResponse;
import org.springframework.data.domain.Pageable;

public interface InsuranceCompanyFinderService {
    InsuranceCompanyResponse findAll(Pageable pageable, Long clinicId);

    InsuranceCompanyModel findById(Long insuranceCompanyId, Long clinicId);

}
