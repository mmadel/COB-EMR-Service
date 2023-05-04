package com.cob.emr.service.insurance.company.finder;

import com.cob.emr.model.insurancecompnay.InsuranceCompanyModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InsuranceCompanyFinderServiceImpl implements InsuranceCompanyFinderService {
    @Override
    public List<InsuranceCompanyModel> findAll(Long clinicId) {
        return null;
    }

    @Override
    public InsuranceCompanyModel findById(Long insuranceCompanyId, Long clinicId) {
        return null;
    }
}
