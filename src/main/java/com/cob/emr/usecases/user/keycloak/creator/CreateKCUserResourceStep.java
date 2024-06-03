package com.cob.emr.usecases.user.keycloak.creator;

import com.cob.emr.exception.business.UserException;
import com.cob.emr.model.user.keycloak.KeyCloakUser;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
@Slf4j
public class CreateKCUserResourceStep extends KeycloakUserCreationStepper {
    @Override
    public boolean create(KeyCloakUser keyCloakUser) throws UserException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        UsersResource usersResource = realmResource().users();
        UserRepresentation userRepresentation = createUserRepresentation(keyCloakUser);
        Response response = usersResource.create(userRepresentation);
        try {
            keyCloakUser.setUuid(CreatedResponseUtil.getCreatedId(response));
            this.uuid = CreatedResponseUtil.getCreatedId(response);

        } catch (WebApplicationException ex) {
            log.error("check the Keycloak ", ex.getMessage());
            throw new UserException(HttpStatus.CONFLICT, UserException.USER_NOT_CREATED_IN_KC, new Object[]{keyCloakUser.getUserName()});
        }
        return nextStep(keyCloakUser);
    }

    private UserRepresentation createUserRepresentation(KeyCloakUser keyCloakUser) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setUsername(keyCloakUser.getUserName());
        userRepresentation.setFirstName(keyCloakUser.getFirstName());
        userRepresentation.setLastName(keyCloakUser.getLastName());
        userRepresentation.setEmail(keyCloakUser.getEmail());
        return userRepresentation;
    }
}
