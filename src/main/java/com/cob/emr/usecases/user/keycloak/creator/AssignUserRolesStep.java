package com.cob.emr.usecases.user.keycloak.creator;

import com.cob.emr.exception.business.UserException;
import com.cob.emr.model.user.keycloak.KeyCloakUser;
import com.cob.emr.usecases.user.keycloak.DeleteKeyCloakUserUseCase;
import com.cob.emr.usecases.user.keycloak.RetrieveKeyCloakRoleRepresentationUseCase;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Component
public class AssignUserRolesStep extends KeycloakUserCreationStepper {
    @Value("${kc.emr.client}")
    private String emrClient;
    @Autowired
    RetrieveKeyCloakRoleRepresentationUseCase retrieveKeyCloakRoleRepresentationUseCase;
    @Autowired
    DeleteKeyCloakUserUseCase deleteKeyCloakUserUseCase;
    @Override
    public boolean create(KeyCloakUser keyCloakUser) throws UserException {
        ClientRepresentation clientRepresentation = realmResource().clients().findByClientId(emrClient).get(0);
        try {
            if(keyCloakUser.getRoles() != null){
                List<RoleRepresentation> roleRepresentations = retrieveKeyCloakRoleRepresentationUseCase.find(keyCloakUser.getRoles(), realmResource(), clientRepresentation);
                getUserResource(keyCloakUser.getUuid()).roles().clientLevel(clientRepresentation.getId()).add(roleRepresentations);
            }
        } catch (javax.ws.rs.NotFoundException exception) {
            deleteKeyCloakUserUseCase.delete(keyCloakUser.getUuid());
            String message = exception.getMessage().split("error\":")[0].replace("}", "")
                    .replace("\"", "");
            throw new UserException(HttpStatus.BAD_REQUEST, UserException.INVALID_ROLE, new Object[]{message});
        }
        return nextStep(keyCloakUser);
    }

    private UserResource getUserResource(String uuid) {
        UsersResource usersResource = realmResource().users();
        UserResource userResource = usersResource.get(uuid);
        return userResource;
    }
}
