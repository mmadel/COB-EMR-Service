package com.cob.emr.usecases.patient;

import com.cob.emr.model.patient.PatientModel;
import com.cob.emr.service.patient.updater.PatientUpdaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class UpdatePatientUseCase {
    @Autowired
    PatientUpdaterService updaterService;
    @PreAuthorize("hasAnyRole(@roleEvaluator.findAuthorizedRoleForModification('patient'))")
    public PatientModel update(PatientModel model){
        return updaterService.update(model);
    }
}
