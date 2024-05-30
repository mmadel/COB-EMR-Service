package com.cob.emr.usecases.user.keycloak;

import com.cob.emr.Utils.BeanFactory;
import com.cob.emr.exception.business.UserException;
import com.cob.emr.model.user.ClinicalEMRUserModel;
import com.cob.emr.model.user.EMRUseRoleScope;
import com.cob.emr.model.user.EMRUser;
import com.cob.emr.model.user.keycloak.KeyCloakUser;
import com.cob.emr.usecases.user.keycloak.creator.AssignUserRolesStep;
import com.cob.emr.usecases.user.keycloak.creator.CreateKCUserResourceStep;
import com.cob.emr.usecases.user.keycloak.creator.CreateUserCredentialsStep;
import com.cob.emr.usecases.user.keycloak.creator.KeycloakUserCreationStepper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CreateKeyCloakUserUseCase {
    public void create(EMRUser model) throws UserException {
        KeyCloakUser keyCloakUser = KeyCloakUser.builder()
                .userName(model.getAccountName())
                .firstName(model.getFirstName())
                .lastName(model.getLastName())
                .email(model.getEmail())
                .password(model.getPassword())
                .userId(model.getUuid())
                .roles(model.getRoleScope().stream()
                        .map(EMRUseRoleScope::getRole)
                        .collect(Collectors.toList()))
                .build();
        KeycloakUserCreationStepper keycloakUserCreationStepper = KeycloakUserCreationStepper.register(
                BeanFactory.getBean(CreateKCUserResourceStep.class),
                BeanFactory.getBean(CreateUserCredentialsStep.class),
                BeanFactory.getBean(AssignUserRolesStep.class)
        );
        keycloakUserCreationStepper.create(keyCloakUser);
        model.setUuid(keycloakUserCreationStepper.getUUID());
    }

}
