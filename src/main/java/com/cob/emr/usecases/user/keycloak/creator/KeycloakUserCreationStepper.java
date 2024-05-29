package com.cob.emr.usecases.user.keycloak.creator;

import com.cob.emr.Utils.BeanFactory;
import com.cob.emr.exception.business.UserException;
import com.cob.emr.model.user.keycloak.KeyCloakUser;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;

public abstract class KeycloakUserCreationStepper {
    private KeycloakUserCreationStepper next;
    protected String uuid;

    public static KeycloakUserCreationStepper register(KeycloakUserCreationStepper first, KeycloakUserCreationStepper... chain) {
        KeycloakUserCreationStepper head = first;
        for (KeycloakUserCreationStepper nextInChain : chain) {
            head.next = nextInChain;
            head = nextInChain;
        }
        return first;
    }

    public abstract boolean create(KeyCloakUser keyCloakUser) throws UserException;

    public String getUUID(){
        return uuid;
    }
    protected RealmResource realmResource() {
        Keycloak keycloakService = BeanFactory.getBean(Keycloak.class);
        return keycloakService.realm("COB");
    }

    protected boolean nextStep(KeyCloakUser model) throws UserException {
        if (next == null) {
            return true;
        }
        return next.create(model);
    }
}
