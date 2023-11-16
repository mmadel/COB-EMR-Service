package com.cob.emr.service.patient.updater;


import com.cob.emr.entity.patient.Patient;
import com.cob.emr.model.patient.PatientModel;
import com.cob.emr.repositories.patient.PatientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PatientUpdaterServiceImpl implements PatientUpdaterService {
    @Autowired
    PatientRepository patientRepository;

    @Autowired
    PatientCaseUpdaterService patientCaseUpdaterService;

    @Autowired
    PatientClinicUpdaterService patientClinicUpdaterService;
    @Autowired
    PatientInsuranceUpdaterService patientInsuranceUpdaterService;

    @Autowired
    ModelMapper mapper;

    @Override
    public PatientModel update(PatientModel submitted) {
        Patient original = patientRepository.findById(submitted.getId())
                .orElseThrow(() -> new IllegalArgumentException("Patient with Id " + submitted.getId() + " not found"));


        patientClinicUpdaterService.update(original, submitted.getClinicsId());

        patientCaseUpdaterService.updatePatientCases(original, submitted.getCases());

        patientInsuranceUpdaterService.updatePatientInsurances(original, submitted.getPatientInsuranceModels());
        Patient created = patientRepository.save(mapper.map(submitted, Patient.class));
        return mapper.map(created, PatientModel.class);
    }

}
