package com.cob.emr.usecases.patient;

import com.cob.emr.model.patient.PatientModel;
import com.cob.emr.service.patient.creator.PatientCreatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class CreatePatientUseCase {
    @Autowired
    PatientCreatorService creatorService;
    @PreAuthorize("hasAnyRole(@roleEvaluator.findAuthorizedRoleForCreation('patient'))")
    public PatientModel create(PatientModel model){
        return creatorService.create(model);
    }
}
