package com.cob.emr.usecases.clinic;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.model.clinic.ClinicModel;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.security.ClericalEMRUserRepository;
import com.cob.emr.repositories.security.ClinicalEMRUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FindClinicsForUserUseCase {

    @Autowired
    ModelMapper mapper;

    @Autowired
    ClinicRepository clinicRepository;
    @Autowired
    ClinicalEMRUserRepository clinicalEMRUserRepository;
    @Autowired
    ClericalEMRUserRepository clericalEMRUserRepository;


    public List<ClinicModel> find(String userId) {
        List<ClinicModel> models;
        models = findClericalUserClinics(userId);
        if (models == null)
            models = findClinicalUserClinics(userId);
        return models;
    }

    private List<ClinicModel> findClericalUserClinics(String userId) {
        List<Clinic> clinics = clericalEMRUserRepository.findUserClinicsByUUID(userId);
        if (clinics.isEmpty())
            return null;
        else
            return clinics
                    .stream()
                    .map(clinic -> mapper.map(clinic, ClinicModel.class))
                    .collect(Collectors.toList());
    }

    private List<ClinicModel> findClinicalUserClinics(String userId) {
        List<Clinic> clinics = clinicalEMRUserRepository.findUserClinicsByUUID(userId);
        if (clinics.isEmpty())
            return null;
        else
            return clinics
                    .stream()
                    .map(clinic -> mapper.map(clinic, ClinicModel.class))
                    .collect(Collectors.toList());
    }
}
