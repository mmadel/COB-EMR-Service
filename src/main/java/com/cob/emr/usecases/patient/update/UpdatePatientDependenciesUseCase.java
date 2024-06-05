package com.cob.emr.usecases.patient.update;

import com.cob.emr.entity.patient.Patient;
import com.cob.emr.model.patient.PatientModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UpdatePatientDependenciesUseCase {
    @Autowired
    private UpdatePatientAddressUseCase updatePatientAddressUseCase;
    @Autowired
    private UpdatePatientContactUseCase updatePatientContactUseCase;

    public void update(PatientModel model, Patient patient) {
        List<PatientDependencyUpdater> dependencies = new ArrayList<>();
        dependencies.add(updatePatientAddressUseCase);
        dependencies.add(updatePatientContactUseCase);
        dependencies.forEach(patientDependencyUpdater -> patientDependencyUpdater.update(model, patient));
    }
}
