package com.cob.emr.usecases.user;

import com.cob.emr.exception.business.UserException;
import com.cob.emr.exception.business.UserKeyCloakException;
import com.cob.emr.model.user.ClinicalUserModel;
import com.cob.emr.repositories.security.ClinicalUserRepository;
import com.cob.emr.usecases.user.keycloak.KeyCloakUsersRemoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class DeleteUserUseCase {

    @Autowired
    KeyCloakUsersRemoverService keyCloakUsersRemoverService;
    @Autowired
    ClinicalUserRepository clinicalUserRepository;

    @Transactional(rollbackOn = {UserKeyCloakException.class, UserException.class})
    public String delete(String uuid) throws UserException {
        clinicalUserRepository.deleteByUUID(uuid);
        keyCloakUsersRemoverService.removeKCUser(uuid);
        return uuid;
    }
}
