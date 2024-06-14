package com.cob.emr.usecases.user.checking;

import com.cob.emr.exception.business.UserException;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CheckEmailUseCase {
    @Autowired
    Keycloak keycloakService;

    public boolean check(String email) throws UserException {
        RealmResource realmResource = keycloakService.realm("COB");
        List<UserRepresentation> users = realmResource.users().search(null, null, null, email, null, null);
        return users.isEmpty();
    }
}
