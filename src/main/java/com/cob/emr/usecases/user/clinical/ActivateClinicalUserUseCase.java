package com.cob.emr.usecases.user.clinical;

import com.cob.emr.entity.security.user.ClinicalEMRUser;
import com.cob.emr.enums.DoctorStatus;
import com.cob.emr.exception.business.UserException;
import com.cob.emr.repositories.security.ClinicalEMRUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class ActivateClinicalUserUseCase {
    @Autowired
    ClinicalEMRUserRepository clinicalEMRUserRepository;

    @Transactional
    public void activate(String uuid) throws UserException {
        ClinicalEMRUser clinicalEMRUser = clinicalEMRUserRepository.findByUUID(uuid)
                .orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, UserException.USER_NOT_FOUND, new Object[]{}));
        clinicalEMRUser.setDoctorStatus(DoctorStatus.DOCTOR_ACTIVE);
        clinicalEMRUserRepository.save(clinicalEMRUser);
    }
}
