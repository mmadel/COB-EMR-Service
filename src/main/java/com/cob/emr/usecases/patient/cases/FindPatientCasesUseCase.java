package com.cob.emr.usecases.patient.cases;

import com.cob.emr.entity.patient.PatientCase;
import com.cob.emr.model.patient.cases.PatientCaseModel;
import com.cob.emr.repositories.patient.cases.PatientCaseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindPatientCasesUseCase {
    @Autowired
    PatientCaseRepository patientCaseRepository;
    @Autowired
    ModelMapper mapper;

    public List<PatientCaseModel> find(Long patientId) {
        List<PatientCase> patientCases = patientCaseRepository.findByPatient_Id(patientId);
        return patientCases.stream()
                .map(patientCase -> {
                    PatientCaseModel patientCaseModel = mapper.map(patientCase, PatientCaseModel.class);
                    patientCaseModel.setTherapist(patientCase.getClinicalEMRUser() ==null?null:patientCase.getClinicalEMRUser().getId());
                    patientCaseModel.setTherapistUUID(patientCase.getClinicalEMRUser() ==null?null:patientCase.getClinicalEMRUser().getUuid());
                    return patientCaseModel;
                })
                .collect(Collectors.toList());
    }
}
