package com.cob.emr.usecases.patient;

import com.cob.emr.Utils.PatientUtil;
import com.cob.emr.model.clinic.ClinicModel;
import com.cob.emr.model.patient.PatientModel;
import com.cob.emr.repositories.patient.PatientRepository;
import com.cob.emr.usecases.patient.cases.FindPatientCasesUseCase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindPatientByNameUseCase {
    @Autowired
    FindPatientCasesUseCase findPatientCasesUseCase;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    ModelMapper mapper;

    public List<PatientModel> find(String name,Long clinicID) {
        List<PatientModel> results = new ArrayList<>();
        patientRepository.findByName(name.trim().toLowerCase(),clinicID).stream()
                .forEach(patient -> {
                    PatientModel model = mapper.map(patient, PatientModel.class);
                    model.setDisplayName(PatientUtil.constructDisplayName(patient.getLastName(), patient.getFirstName() , patient.getBirthDate()));
                    List<ClinicModel> clinicModels = patient.getClinics().stream()
                            .map(clinicEntity -> mapper.map(clinicEntity , ClinicModel.class))
                            .collect(Collectors.toList());
                    model.setCases(findPatientCasesUseCase.find(patient.getId()));
                    model.setClinicModels(clinicModels);
                    results.add(model);
                });
        return results;
    }
}
