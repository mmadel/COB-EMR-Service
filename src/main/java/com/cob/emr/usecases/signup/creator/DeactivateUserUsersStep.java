package com.cob.emr.usecases.signup.creator;

import com.cob.emr.exception.business.UserException;
import com.cob.emr.model.organization.OrganizationRequest;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
public class DeactivateUserUsersStep extends OrganizationRequestCreator {
    @Autowired
    Keycloak keycloakService;
    @Value("${kc.realm}")
    private String realm;
    @Value("${kc.emr.client}")
    private String emrClient;

    @Override
    public boolean create(OrganizationRequest model) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        RealmResource realmResource = keycloakService.realm(realm);
        UsersResource usersResource = realmResource.users();
        UserResource userResource = usersResource.get(model.getLoggedInUUID());
        UserRepresentation userRepresentation = userResource.toRepresentation();
        userRepresentation.setEnabled(false);
        userResource.update(userRepresentation);
        return nextStep(model);
    }
}
