package com.cob.emr.usecases.user.keycloak;

import com.cob.emr.model.user.EMRUser;
import com.cob.emr.usecases.user.keycloak.updater.AddRoleUseCase;
import com.cob.emr.usecases.user.keycloak.updater.RemoveRoleUseCase;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UpdateKeyCloakUserUseCase {
    @Autowired
    Keycloak keycloakService;
    @Value("${kc.realm}")
    private String realm;
    @Value("${kc.emr.client}")
    private String emrClient;
    @Autowired
    AddRoleUseCase addRoleUseCase;
    @Autowired
    RemoveRoleUseCase removeRoleUseCase;

    public void update(EMRUser model) {
        RealmResource realmResource = keycloakService.realm(realm);
        ClientRepresentation clientRepresentation = realmResource.clients().findByClientId(emrClient).get(0);

        List<String> removedRoles = model.getRoleScope().stream()
                .filter(roleScope -> roleScope.getScope().equals("hidden"))
                .map(roleScope -> roleScope.getRole())
                .collect(Collectors.toList());
        if (removedRoles != null)
            removeRoleUseCase.remove(model.getUuid(), removedRoles, realmResource, clientRepresentation);

        List<String> addedRoles = model.getRoleScope().stream()
                .filter(roleScope -> !roleScope.getScope().equals("hidden"))
                .map(roleScope -> roleScope.getRole())
                .collect(Collectors.toList());
        if (addedRoles != null)
            addRoleUseCase.add(model.getUuid(), addedRoles, realmResource, clientRepresentation);
    }
}
