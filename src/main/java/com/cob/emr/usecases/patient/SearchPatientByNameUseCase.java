package com.cob.emr.usecases.patient;

import com.cob.emr.model.patient.PatientModel;
import com.cob.emr.model.response.PatientResponse;
import com.cob.emr.repositories.patient.PatientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SearchPatientByNameUseCase {
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    ModelMapper mapper;

    public PatientResponse search(String name, Long clinicId) {
        List<PatientModel> records = patientRepository.searchByName(clinicId, name.trim().toLowerCase())
                .stream()
                .map(patient -> {
                    PatientModel model = mapper.map(patient, PatientModel.class);
                    model.setClinicsId(patient.getClinics()
                            .stream()
                            .map(clinic -> clinic.getId()).collect(Collectors.toList()));
                    return model;
                })
                .collect(Collectors.toList());
        return PatientResponse.builder()
                .number_of_records(records.size())
                .number_of_matching_records(records.size())
                .records(records)
                .build();
    }
}
