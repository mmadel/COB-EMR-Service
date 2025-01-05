package com.cob.emr.usecases.patient;

import com.cob.emr.model.patient.PatientModel;
import com.cob.emr.repositories.patient.PatientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FindPatientByNameUseCase {
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    ModelMapper mapper;

    public List<PatientModel> find(String name) {
        List<PatientModel> results = new ArrayList<>();
        patientRepository.findByName(name.trim().toLowerCase()).stream()
                .forEach(patient -> {
                    results.add(mapper.map(patient, PatientModel.class));
                });
        return results;
    }
}
