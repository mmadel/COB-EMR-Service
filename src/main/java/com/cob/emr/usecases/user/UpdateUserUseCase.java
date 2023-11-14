package com.cob.emr.usecases.user;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.security.user.ClinicalUser;
import com.cob.emr.model.clinic.ClinicModel;
import com.cob.emr.model.user.ClinicalUserModel;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.security.ClinicalUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class UpdateUserUseCase {
    @Autowired
    ClinicalUserRepository clinicalUserRepository;
    @Autowired
    ClinicRepository clinicRepository;
    @Autowired
    ModelMapper mapper;

    public void update(ClinicalUserModel model) {
        ClinicalUser clinicalUser = clinicalUserRepository.findById(model.getId()).get();
        List<ClinicModel> fromDatabase = clinicalUser.getClinics()
                .stream().map(clinic -> mapper.map(clinic, ClinicModel.class))
                .collect(Collectors.toList());
        List<ClinicModel> fromUser = model.getClinics().stream()
                .map(id -> clinicRepository.findById(Long.parseLong(id)).get())
                .collect(Collectors.toList())
                .stream().map(clinic -> mapper.map(clinic, ClinicModel.class))
                .collect(Collectors.toList());
        assignAddedClinics(clinicalUser,fromUser,fromDatabase);
        unAssignRemovedClinics(clinicalUser, fromDatabase,fromUser);
    }

    private void assignAddedClinics(ClinicalUser user, List<ClinicModel> fromUser, List<ClinicModel> fromDatabase) {
        List<ClinicModel> clinicModels = findClinicsDifference(fromUser, fromDatabase);
        clinicModels
                .stream()
                .map(model -> clinicRepository.findById(Long.valueOf(model.getId())).get())
                .collect(Collectors.toSet())
                .forEach(clinic -> user.addClinic(clinic));
        clinicalUserRepository.save(user);
    }

    private void unAssignRemovedClinics(ClinicalUser user, List<ClinicModel> fromDatabase, List<ClinicModel> fromUser) {
        List<ClinicModel> clinicModels = findClinicsDifference(fromDatabase, fromUser);
        clinicModels
                .stream()
                .map(model -> clinicRepository.findById(Long.valueOf(model.getId())).get())
                .collect(Collectors.toSet())
                .forEach(clinic -> user.removeClinic(clinic));
        clinicalUserRepository.save(user);
    }

    private List<ClinicModel> findClinicsDifference(List<ClinicModel> first, List<ClinicModel> second) {
        List<ClinicModel> difference = new ArrayList<>();
        for (ClinicModel clinicModel : first) {
            if (!second.contains(clinicModel)) {
                difference.add(clinicModel);
            }
        }
        return difference;
    }
}
