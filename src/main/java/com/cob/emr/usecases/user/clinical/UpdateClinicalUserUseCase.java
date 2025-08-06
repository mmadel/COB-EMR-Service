package com.cob.emr.usecases.user.clinical;

import com.cob.emr.entity.security.user.ClinicalEMRUser;
import com.cob.emr.model.user.ClinicalEMRUserModel;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.security.ClinicalEMRUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UpdateClinicalUserUseCase {
    @Autowired
    ClinicalEMRUserRepository clinicalEMRUserRepository;
    @Autowired
    ClinicRepository clinicRepository;
    @Autowired
    ModelMapper mapper;

    public void update(ClinicalEMRUserModel model) {
        ClinicalEMRUser clinicalEMRUser = clinicalEMRUserRepository.findById(model.getId()).get();
        List<Long> updated = model.getClinicIds();
        List<Long> db = clinicalEMRUser.getClinics().stream()
                .map(clinic -> clinic.getId())
                .collect(Collectors.toList());
        findAddedClinics(db, updated, clinicalEMRUser);
        findDeletedClinics(db, updated, clinicalEMRUser);
        fillUser(clinicalEMRUser, model);
        clinicalEMRUserRepository.save(clinicalEMRUser);
    }

    private void fillUser(ClinicalEMRUser clinicalEMRUser, ClinicalEMRUserModel model) {
        clinicalEMRUser.setRoleScope(model.getRoleScope());
        clinicalEMRUser.setNpi(model.getNpi());
        clinicalEMRUser.setLicence(model.getLicence());
        clinicalEMRUser.setSpeciality(model.getSpeciality());
        clinicalEMRUser.setCredential(model.getCredential());
    }

    private void findAddedClinics(List<Long> dbList, List<Long> modifiedList, ClinicalEMRUser clinicalEMRUser) {
        List<Long> newItems = modifiedList.stream()
                .filter(item -> !dbList.contains(item))
                .collect(Collectors.toList());
        newItems
                .stream()
                .map(clinicId -> clinicRepository.findById(clinicId).get())
                .collect(Collectors.toSet())
                .forEach(clinic -> clinicalEMRUser.addClinic(clinic));
    }

    private void findDeletedClinics(List<Long> dbList, List<Long> modifiedList, ClinicalEMRUser clinicalEMRUser) {
        List<Long> deletedItems = dbList.stream()
                .filter(item -> !modifiedList.contains(item))
                .collect(Collectors.toList());
        deletedItems
                .stream()
                .map(clinicId -> clinicRepository.findById(clinicId).get())
                .collect(Collectors.toSet())
                .forEach(clinic -> clinicalEMRUser.removeClinic(clinic));
    }
}
