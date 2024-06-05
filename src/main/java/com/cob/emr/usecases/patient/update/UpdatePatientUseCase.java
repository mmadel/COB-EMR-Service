package com.cob.emr.usecases.patient.update;

import com.cob.emr.entity.patient.Patient;
import com.cob.emr.model.patient.PatientModel;
import com.cob.emr.repositories.patient.PatientRepository;
import com.cob.emr.service.patient.updater.PatientUpdaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UpdatePatientUseCase {
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    UpdatePatientDependenciesUseCase updatePatientDependenciesUseCase;

    public void update(PatientModel model) {
        Patient patient = patientRepository.findById(model.getId()).get();

        updatePatientDependenciesUseCase.update(model, patient);

        patientRepository.save(patient);
    }
}
