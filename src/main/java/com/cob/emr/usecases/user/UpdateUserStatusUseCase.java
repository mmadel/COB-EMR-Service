package com.cob.emr.usecases.user;

import com.cob.emr.entity.security.user.ClericalEMRUser;
import com.cob.emr.entity.security.user.ClinicalEMRUser;
import com.cob.emr.enums.UserStatus;
import com.cob.emr.exception.business.UserException;
import com.cob.emr.repositories.security.ClericalEMRUserRepository;
import com.cob.emr.repositories.security.ClinicalEMRUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UpdateUserStatusUseCase {

    @Autowired
    private ClinicalEMRUserRepository clinicalEMRUserRepository;

    @Autowired
    private ClericalEMRUserRepository clericalEMRUserRepository;

    public void update(String uuid, UserStatus status) throws UserException {
        Optional<ClinicalEMRUser> clinicalUser = clinicalEMRUserRepository.findByUUID(uuid);
        if (clinicalUser.isPresent()) {
            clinicalUser.get().setStatus(status);
            clinicalEMRUserRepository.save(clinicalUser.get());
            return;
        }

        Optional<ClericalEMRUser> clericalUser = clericalEMRUserRepository.findByUUID(uuid);
        if (clericalUser.isPresent()) {
            clericalUser.get().setStatus(status);
            clericalEMRUserRepository.save(clericalUser.get());
            return;
        }

        throw new UserException(HttpStatus.NOT_FOUND, UserException.USER_NOT_FOUND, new Object[]{});
    }
}
