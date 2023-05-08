package com.cob.emr.service.patient.updater;


import com.cob.emr.Utils.ListUtils;
import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.patient.Patient;
import com.cob.emr.entity.patient.PatientCase;
import com.cob.emr.entity.patient.PatientInsurance;
import com.cob.emr.model.patient.PatientModel;
import com.cob.emr.model.patient.cases.PatientCaseModel;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.patient.PatientRepository;
import com.cob.emr.repositories.patient.cases.PatientCaseRepository;
import com.cob.emr.repositories.patient.insurance.PatientInsuranceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class PatientUpdaterServiceImpl implements PatientUpdaterService {
    @Autowired
    PatientRepository patientRepository;

    @Autowired
    ClinicRepository clinicRepository;

    @Autowired
    PatientCaseRepository patientCaseRepository;
    @Autowired
    PatientInsuranceRepository patientInsuranceRepository;
    @Autowired
    ModelMapper mapper;

    @Override
    public PatientModel update(PatientModel submitted) {
        Patient original = patientRepository.findById(submitted.getId())
                .orElseThrow(() -> new IllegalArgumentException("Patient with Id " + submitted.getId() + " not found"));
        updateClinics(original, submitted);

        updateCases(original, submitted);
        updateInsurances(submitted);
        /*Patient toBeSaved = mapper.map(submitted, Patient.class);
        patientRepository.save(toBeSaved);*/

        return null;
    }

    private void updateClinics(Patient original, PatientModel submitted) {
        List<Long> originalClinicIds = original.getClinics()
                .stream()
                .map(Clinic::getId).collect(Collectors.toList());

        List<Long> submittedClinicsIds = submitted.getClinicsId();
        /*
            assign returned from DB to source list
         */
        addClinics(original, submittedClinicsIds, originalClinicIds);
        /*
            assign  submitted to source list
         */
        removeClinics(original, originalClinicIds, submittedClinicsIds);
        patientRepository.save(original);
    }

    private void updateCases(Patient original, PatientModel submitted) {
        /*
            Delete removed cases
         */
        List<Long> originalIds = patientCaseRepository.findByPatient_Id(submitted.getId())
                .stream()
                .map(PatientCase::getId)
                .collect(Collectors.toList());
        List<Long> persistedSubmittedIds = submitted.getPatientCaseModels()
                .stream()
                .map(PatientCaseModel::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        List<Long> toBeDeleted = ListUtils.returnDifference(originalIds, persistedSubmittedIds);

        if (toBeDeleted.size() > 0)
            patientCaseRepository.deleteAllById(toBeDeleted);

        /*
            Update persisted cases
         */
        List<PatientCase> persistedSubmittedCases = submitted.getPatientCaseModels()
                .stream()
                .filter(patientInsuranceModel -> patientInsuranceModel.getId() != null)
                .map(patientCaseModel -> {
                    PatientCase toBeUpdated = mapper.map(patientCaseModel, PatientCase.class);
                    toBeUpdated.setPatient(original);
                    return toBeUpdated;
                })
                .collect(Collectors.toList());
        if (persistedSubmittedCases.size() > 0)
            patientCaseRepository.saveAll(persistedSubmittedCases);
        /*
            Persist new cases
         */
        List<PatientCase> newSubmittedCases = submitted.getPatientCaseModels()
                .stream()
                .filter(patientCaseModel -> patientCaseModel.getId() == null)
                .map(patientCaseModel -> {
                    PatientCase toBeSaved = mapper.map(patientCaseModel, PatientCase.class);
                    toBeSaved.setPatient(original);
                    return toBeSaved;
                })
                .collect(Collectors.toList());

        if (newSubmittedCases.size() > 0)
            patientCaseRepository.save(newSubmittedCases.get(0));
    }

    private void updateInsurances(PatientModel submitted) {
        patientInsuranceRepository.saveAll(
                submitted.getPatientInsuranceModels()
                        .stream()
                        .map(patientInsuranceModel -> mapper.map(patientInsuranceModel, PatientInsurance.class))
                        .collect(Collectors.toList()));
    }

    private void addClinics(Patient patient, List<Long> source, List<Long> destination) {
        List<Long> clinicsIdsToBeAdd = ListUtils.returnDifference(source, destination);
        if (clinicsIdsToBeAdd.size() > 0)
            clinicRepository.findAllById(clinicsIdsToBeAdd)
                    .forEach(patient::addClinic);
    }

    private void removeClinics(Patient patient, List<Long> source, List<Long> destination) {
        List<Long> clinicsIdsToBeRemoved = ListUtils.returnDifference(source, destination);
        if (clinicsIdsToBeRemoved.size() > 0)
            clinicRepository.findAllById(clinicsIdsToBeRemoved)
                    .forEach(patient::removeClinic);
    }
}
