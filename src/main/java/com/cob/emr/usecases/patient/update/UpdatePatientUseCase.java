package com.cob.emr.usecases.patient.update;

import com.cob.emr.entity.patient.Patient;
import com.cob.emr.exception.business.PatientCaseException;
import com.cob.emr.model.patient.PatientModel;
import com.cob.emr.repositories.patient.PatientRepository;
import com.cob.emr.usecases.patient.update.cases.UpdatePatientCaseUseCase;
import com.cob.emr.usecases.patient.update.dependencies.UpdatePatientDependenciesUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdatePatientUseCase {
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    UpdatePatientDependenciesUseCase updatePatientDependenciesUseCase;
    @Autowired
    UpdatePatientCaseUseCase updatePatientCaseUseCase;

    public void update(PatientModel model) throws PatientCaseException {
        Patient patient = patientRepository.findById(model.getId()).get();

        //updatePatientDependenciesUseCase.update(model, patient);
        updatePatientCaseUseCase.update(model, patient);

        patientRepository.save(patient);
    }
}
