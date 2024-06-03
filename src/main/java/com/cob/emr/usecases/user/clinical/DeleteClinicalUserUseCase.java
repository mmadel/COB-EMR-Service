package com.cob.emr.usecases.user.clinical;

import com.cob.emr.entity.security.user.ClinicalEMRUser;
import com.cob.emr.repositories.security.ClinicalEMRUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class DeleteClinicalUserUseCase {
    @Autowired
    ClinicalEMRUserRepository clinicalEMRUserRepository;

    @Transactional
    public void delete(String uuid) {
        ClinicalEMRUser clinicalEMRUser = clinicalEMRUserRepository.findByUUID(uuid).get();
        clinicalEMRUser.getClinics()
                        .forEach(clinic -> {
                            clinic.removeClinicalUser(clinicalEMRUser);
                        });
        clinicalEMRUserRepository.delete(clinicalEMRUser);
    }
}
