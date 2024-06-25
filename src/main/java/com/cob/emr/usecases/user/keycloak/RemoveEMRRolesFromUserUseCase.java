package com.cob.emr.usecases.user.keycloak;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RemoveEMRRolesFromUserUseCase {
    @Autowired
    Keycloak keycloakService;
    @Value("${kc.realm}")
    private String realm;
    @Value("${kc.emr.client}")
    private String emrClient;

    public void removeAll(String uuid){
        RealmResource realmResource = keycloakService.realm(realm);
        ClientRepresentation clientRepresentation = realmResource.clients().findByClientId(emrClient).get(0);
        getUserResource(uuid, realmResource).roles().clientLevel(clientRepresentation.getId()).remove(realmResource.clients().get(clientRepresentation.getId()).roles().list());
    }

    private UserResource getUserResource(String uuid, RealmResource realmResource) {
        UsersResource usersResource = realmResource.users();
        UserResource userResource = usersResource.get(uuid);
        return userResource;
    }
}
