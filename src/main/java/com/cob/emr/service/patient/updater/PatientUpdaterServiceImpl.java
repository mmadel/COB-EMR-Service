package com.cob.emr.service.patient.updater;


import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.patient.Patient;
import com.cob.emr.model.patient.PatientModel;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.patient.PatientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PatientUpdaterServiceImpl implements PatientUpdaterService {
    @Autowired
    PatientRepository patientRepository;

    @Autowired
    ClinicRepository clinicRepository;
    @Autowired
    ModelMapper mapper;

    @Override
    public PatientModel update(PatientModel model) {
        Patient original = patientRepository.findById(model.getId())
                .orElseThrow(() -> new IllegalArgumentException("Patient with Id " + model.getId() + " not found"));
        updateClinics(
                original, original.getClinics()
                        .stream()
                        .map(Clinic::getId).collect(Collectors.toList()),
                model.getClinicsId());
        return null;
    }

    private void updateClinics(Patient patient, List<Long> originalClinicIds, List<Long> submittedClinicsId) {
        /*
            assign source list returned from DB
         */
        addClinics(patient, submittedClinicsId, originalClinicIds);
        /*
            assign source list returned from DB
         */
        removeClinics(patient, originalClinicIds, submittedClinicsId);
        patientRepository.save(patient);
    }

    private void addClinics(Patient patient, List<Long> source, List<Long> destination) {
        List<Long> clinicsIdsToBeAdd = patient.clinicDifferences(source, destination);
        if (clinicsIdsToBeAdd.size() > 0)
            clinicRepository.findAllById(clinicsIdsToBeAdd)
                    .forEach(patient::addClinic);
    }

    private void removeClinics(Patient patient, List<Long> source, List<Long> destination) {
        List<Long> clinicsIdsToBeRemoved = patient.clinicDifferences(source, destination);
        if (clinicsIdsToBeRemoved.size() > 0)
            clinicRepository.findAllById(clinicsIdsToBeRemoved)
                    .forEach(patient::removeClinic);
    }
}
