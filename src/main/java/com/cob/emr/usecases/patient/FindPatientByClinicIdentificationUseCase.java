package com.cob.emr.usecases.patient;

import com.cob.emr.model.response.PatientResponse;
import com.cob.emr.service.patient.finder.PatientFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class FindPatientByClinicIdentificationUseCase {
    @Autowired
    PatientFinderService finderService;
    @PreAuthorize("hasAnyRole(@roleEvaluator.findAuthorizedRoleForInquiring('patient'))")
    public PatientResponse find(Pageable paging, Long clinicId){
        return finderService.findAll(paging, clinicId);
    }
}
