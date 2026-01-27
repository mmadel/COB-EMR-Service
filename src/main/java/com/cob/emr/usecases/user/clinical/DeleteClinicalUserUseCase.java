package com.cob.emr.usecases.user.clinical;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.security.user.ClinicalEMRUser;
import com.cob.emr.repositories.security.ClinicalEMRUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DeleteClinicalUserUseCase {
    @Autowired
    ClinicalEMRUserRepository clinicalEMRUserRepository;

    @Transactional
    public void delete(String uuid) {
        ClinicalEMRUser clinicalEMRUser = clinicalEMRUserRepository.findByUUID(uuid).get();
        Set<Clinic> copy = new HashSet<>(clinicalEMRUser.getClinics());
        copy
                .forEach(clinic -> {
                    clinic.removeClinicalUser(clinicalEMRUser);
                });
        clinicalEMRUserRepository.delete(clinicalEMRUser);
    }

    @Transactional
    public void deleteByClinic(Long clinicId) {
        List<ClinicalEMRUser> clinicalEMRUsers = clinicalEMRUserRepository.findByClinic(clinicId).get();
        clinicalEMRUsers.forEach(user -> {
            Set<Clinic> copy = new HashSet<>(user.getClinics());
            copy.forEach(clinic -> clinic.removeClinicalUser(user));
        });
        clinicalEMRUserRepository.deleteAllById(clinicalEMRUsers.stream()
                .map(user -> user.getId())
                .collect(Collectors.toList()));
    }
}
