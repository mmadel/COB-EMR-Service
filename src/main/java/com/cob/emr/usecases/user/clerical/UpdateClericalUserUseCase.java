package com.cob.emr.usecases.user.clerical;

import com.cob.emr.entity.security.user.ClericalEMRUser;
import com.cob.emr.entity.security.user.ClinicalEMRUser;
import com.cob.emr.model.user.ClericalEMRUserModel;
import com.cob.emr.model.user.ClinicalEMRUserModel;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.security.ClericalEMRUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UpdateClericalUserUseCase {
    @Autowired
    ClericalEMRUserRepository clericalEMRUserRepository;
    @Autowired
    ClinicRepository clinicRepository;

    public void update(ClericalEMRUserModel model) {
        ClericalEMRUser clericalEMRUser = clericalEMRUserRepository.findById(model.getId()).get();
        List<Long> updated = model.getClinicIds();
        List<Long> db = clericalEMRUser.getClinics().stream()
                .map(clinic -> clinic.getId())
                .collect(Collectors.toList());
        findAddedClinics(db, updated, clericalEMRUser);
        findDeletedClinics(db, updated, clericalEMRUser);
        fillUser(clericalEMRUser, model);
        clericalEMRUserRepository.save(clericalEMRUser);
    }

    private void fillUser(ClericalEMRUser clericalEMRUser, ClericalEMRUserModel model) {
        clericalEMRUser.setRoleScope(model.getRoleScope());
    }

    private void findAddedClinics(List<Long> dbList, List<Long> modifiedList, ClericalEMRUser clericalEMRUser) {
        List<Long> newItems = modifiedList.stream()
                .filter(item -> !dbList.contains(item))
                .collect(Collectors.toList());
        newItems
                .stream()
                .map(clinicId -> clinicRepository.findById(clinicId).get())
                .collect(Collectors.toSet())
                .forEach(clinic -> clericalEMRUser.addClinic(clinic));
    }

    private void findDeletedClinics(List<Long> dbList, List<Long> modifiedList, ClericalEMRUser clericalEMRUser) {
        List<Long> deletedItems = dbList.stream()
                .filter(item -> !modifiedList.contains(item))
                .collect(Collectors.toList());
        deletedItems
                .stream()
                .map(clinicId -> clinicRepository.findById(clinicId).get())
                .collect(Collectors.toSet())
                .forEach(clinic -> clericalEMRUser.removeClinic(clinic));
    }
}
