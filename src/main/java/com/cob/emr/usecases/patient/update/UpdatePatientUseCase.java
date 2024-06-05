package com.cob.emr.usecases.patient.update;

import com.cob.emr.entity.patient.Patient;
import com.cob.emr.model.patient.PatientModel;
import com.cob.emr.repositories.patient.PatientRepository;
import com.cob.emr.service.patient.updater.PatientUpdaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class UpdatePatientUseCase {
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    UpdatePatientAddressUseCase updatePatientAddressUseCase;

    public void update(PatientModel model) {
        Patient patient = patientRepository.findById(model.getId()).get();
        updatePatientAddressUseCase.update(model.getAddresses(), patient.getAddresses(), patient);
        patientRepository.save(patient);
    }
}
