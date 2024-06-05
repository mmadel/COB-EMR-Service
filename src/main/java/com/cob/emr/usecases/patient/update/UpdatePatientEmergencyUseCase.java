package com.cob.emr.usecases.patient.update;

import com.cob.emr.entity.patient.Patient;
import com.cob.emr.model.patient.PatientModel;
import org.springframework.stereotype.Component;

@Component
public class UpdatePatientEmergencyUseCase extends PatientDependencyUpdater {

    @Override
    public void update(PatientModel model, Patient patient) {

    }
}
