package com.cob.emr.usecases.clinic;

import com.cob.emr.model.clinic.ClinicModel;
import com.cob.emr.service.clinic.ClinicFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindClinicsByListOfIdentificationUseCase {
    @Autowired
    ClinicFinderService clinicFinderService;
    @PreAuthorize("hasAnyRole(@roleEvaluator.findAuthorizedRoleForInquiring('clinic'))")
    public List<ClinicModel> find(List<Long> ids){
        return clinicFinderService.findByIds(ids);
    }
}
