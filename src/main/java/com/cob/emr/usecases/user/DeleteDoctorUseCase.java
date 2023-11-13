package com.cob.emr.usecases.user;

import com.cob.emr.exception.business.UserException;
import com.cob.emr.exception.business.UserKeyCloakException;
import com.cob.emr.repositories.security.ClinicalUserRepository;
import com.cob.emr.repositories.security.DoctorUserRepository;
import com.cob.emr.usecases.user.keycloak.KeyCloakUsersRemoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class DeleteDoctorUseCase {
    @Autowired
    KeyCloakUsersRemoverService keyCloakUsersRemoverService;
    @Autowired
    DoctorUserRepository doctorUserRepository;

    @Transactional(rollbackOn = {UserKeyCloakException.class, UserException.class})
    public String delete(String uuid) throws UserException {
        doctorUserRepository.deleteByUUID(uuid);
        keyCloakUsersRemoverService.removeKCUser(uuid);
        return uuid;
    }
}
