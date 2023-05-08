package com.cob.emr.service.patient.finder;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.patient.Patient;
import com.cob.emr.model.patient.PatientModel;
import com.cob.emr.model.patient.cases.PatientCaseModel;
import com.cob.emr.model.patient.insurance.PatientInsuranceModel;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.patient.PatientRepository;
import com.cob.emr.repositories.patient.cases.PatientCaseRepository;
import com.cob.emr.repositories.patient.insurance.PatientInsuranceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientFinderServiceImpl implements PatientFinderService {

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
    public List<PatientModel> findAll(Long clinicId) {

        List<Clinic> clinics = new ArrayList<>();
        clinics.add(clinicRepository
                .findById(clinicId)
                .orElseThrow(() -> new IllegalArgumentException("Clinic with id not found" + clinicId)));
        return patientRepository.findByClinicsIn(clinics)
                .stream().map(patient -> mapper.map(patient, PatientModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public PatientModel findById(Long clinicId, Long patientId) {
        List<Clinic> clinics = new ArrayList<>();
        clinics.add(clinicRepository
                .findById(clinicId)
                .orElseThrow(() -> new IllegalArgumentException("Clinic with id not found" + clinicId)));
        Patient queriedPatient = patientRepository
                .findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Clinic with id not found" + patientId));

        Patient patient = patientRepository.findByIdAndClinicsIn(patientId, clinics).stream().findFirst().orElseThrow(
                () -> new IllegalArgumentException("patient with id not found" + patientId));

        PatientModel model = mapper.map(patient, PatientModel.class);
        model.setPatientCaseModels(patientCaseRepository
                .findByPatient_Id(patientId)
                .stream()
                .map(patientCase -> mapper.map(patientCase, PatientCaseModel.class))
                .collect(Collectors.toList()));

        model.setPatientInsuranceModels(
                patientInsuranceRepository
                        .findByPatient_Id(patientId)
                        .stream()
                        .map(patientInsurance -> mapper.map(patientInsurance, PatientInsuranceModel.class))
                        .collect(Collectors.toList()));
        List<Patient> patients = new ArrayList<>();
        patients.add(queriedPatient);
        model.setClinicsId(
                clinicRepository.findByPatientsIn(patients).stream().map(Clinic::getId).collect(Collectors.toList())
        );
        patientInsuranceRepository.findByPatient_Id(patientId);
        return model;
    }
}
