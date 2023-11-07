package com.cob.emr.usecases.user.keycloak;

import com.cob.emr.Utils.Encryption;
import com.cob.emr.exception.business.UserException;
import com.cob.emr.exception.business.UserKeyCloakException;
import com.cob.emr.model.user.keycloak.Credentials;
import com.cob.emr.model.user.keycloak.KeyCloakUser;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class KeyCloakUserCreationService {
    @Autowired
    Keycloak keycloakService;


    @Value("${kc.url}")
    private String keycloakURL;

    @Value("${kc.realm}")
    private String realm;
    @Autowired
    Encryption encryption;
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    KeyCloakUsersRemoverService keyCloakUsersRemoverService;
    @Autowired
    AuthenticationService authenticationService;

    @Value("${kc.administrator.username}")
    private String admin_username;
    @Value("${kc.administrator.password}")
    private String admin_password;

    public UserRepresentation create(KeyCloakUser keyCloakUser) throws UserException, UserKeyCloakException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        RealmResource realmResource = keycloakService.realm(realm);
        ClientRepresentation clientRepresentation = realmResource.clients().findByClientId("intake-ui").get(0);
        UsersResource usersResource = realmResource.users();
        isUserExists(realmResource, keyCloakUser.getUsername());
        isEmailExists(realmResource, keyCloakUser.getEmail());
        isRoleNotExists(keyCloakUser.getRoles(), realmResource, clientRepresentation);

        UserRepresentation user = prepareUserRepresentation(keyCloakUser);
        Response response = usersResource.create(user);
        String userId = null;
        try {
            userId = CreatedResponseUtil.getCreatedId(response);

        } catch (WebApplicationException ex) {
            log.error("check the Keycloak ", ex.getMessage());
            throw new UserException(HttpStatus.CONFLICT, UserException.USER_NOT_CREATED_IN_KC, new Object[]{keyCloakUser.getUsername()});
        }
        setUserPassword(userId, keyCloakUser.getPassword());

        UserResource userResource = usersResource.get(userId);

        //updateAttribute(userResource, "address", keyCloakUser.getAddress());

        List<RoleRepresentation> roles = null;

        roles = prepareRoleRepresentation(keyCloakUser.getRoles(), realmResource, clientRepresentation);

        userResource.roles().clientLevel(clientRepresentation.getId()).add(roles);
        return userResource.toRepresentation();
    }

    public void update(KeyCloakUser keyCloakUser) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, UserException {
        RealmResource realmResource = keycloakService.realm(realm);
        UsersResource usersResource = realmResource.users();
        UserResource userResource = null;
        try {
            userResource = usersResource.get(keyCloakUser.getUserId());
        } catch (WebApplicationException ex) {
            throw new UserException(HttpStatus.CONFLICT, UserException.USER_NOT_FOUND, new Object[]{keyCloakUser.getUsername()});
        }
        if (keyCloakUser.getPassword() != null) {
            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setTemporary(true);
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(encryption.decrypt(keyCloakUser.getPassword()));
            userResource.resetPassword(credential);

        }
        updateAttribute(userResource, "address", keyCloakUser.getAddress());

        ClientRepresentation clientRepresentation = realmResource.clients().findByClientId("intake-ui").get(0);
        RolesResource oldRole = null;
        try {
            oldRole = realmResource.clients().get(clientRepresentation.getId()).roles();
        } catch (WebApplicationException ex) {
            throw new IllegalArgumentException("old role not found");
        }
        userResource.roles().clientLevel(clientRepresentation.getId()).remove(oldRole.list());
        List<RoleRepresentation> role = null;
        try {
            role = prepareRoleRepresentation(keyCloakUser.getRoles(), realmResource, clientRepresentation);
        } catch (WebApplicationException ex) {
            throw new UserException(HttpStatus.CONFLICT, UserException.USER_ROLE_NOT_FOUND, new Object[]{keyCloakUser.getRoles().get(0)});
        }
        userResource.roles().clientLevel(clientRepresentation.getId()).add(role);

    }

    private void updateAttribute(UserResource userResource, String property, String value) {
        UserRepresentation userRepresentation = userResource.toRepresentation();
        userRepresentation.singleAttribute(property, value);
        userResource.update(userRepresentation);
    }

    private UserRepresentation prepareUserRepresentation(KeyCloakUser keyCloakUser) {
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(keyCloakUser.getUsername());
        user.setFirstName(keyCloakUser.getFirstName());
        user.setLastName(keyCloakUser.getLastName());
        user.setEmail(keyCloakUser.getEmail());
        return user;
    }

    private List<RoleRepresentation> prepareRoleRepresentation(List<String> roles, RealmResource realmResource, ClientRepresentation clientRepresentation) {
        List<RoleRepresentation> roleRepresentation = new ArrayList<>();
        roles.forEach(role -> {
            RoleRepresentation clientRole = realmResource.clients().get(clientRepresentation.getId())
                    .roles().get(role).toRepresentation();
            roleRepresentation.add(clientRole);
        });
        return roleRepresentation;
    }

    private void isUserExists(RealmResource realmResource, String userName) throws UserException {
        List<UserRepresentation> userRepresentations = realmResource.users().list();
        for (UserRepresentation userRepresentation : userRepresentations) {
            if (userRepresentation.getUsername().equals(userName))
                throw new UserException(HttpStatus.CONFLICT, UserException.USER_IS_EXISTS, new Object[]{userName});
        }
    }

    private void isEmailExists(RealmResource realmResource, String email) throws UserException {
        List<UserRepresentation> userRepresentations = realmResource.users().list();
        for (UserRepresentation userRepresentation : userRepresentations) {
            if (userRepresentation.getEmail().equals(email))
                throw new UserException(HttpStatus.CONFLICT, UserException.USER_EMAIL_IS_EXISTS, new Object[]{email});
        }
    }

    private void isRoleNotExists(List<String> roles, RealmResource realmResource, ClientRepresentation clientRepresentation) throws UserException {
        try {
            realmResource.clients().get(clientRepresentation.getId()).roles().get(roles.get(0)).toRepresentation();
        } catch (Exception exception) {
            throw new UserException(HttpStatus.CONFLICT, UserException.USER_ROLE_NOT_FOUND, new Object[]{roles.get(0)});
        }
    }

    private void setUserPassword(String userId, String password) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, UserException, HttpClientErrorException, UserKeyCloakException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authenticationService.getTokens(Credentials.builder()
                .userName(admin_username)
                .password(admin_password)
                .build()).getAccess_token());
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(true);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(encryption.decrypt(password));
        Gson gson = new Gson();
        gson.toJson(credential);
        HttpEntity<String> httpEntity = new HttpEntity<>(gson.toJson(credential).toString(), headers);
        try {
            restTemplate.put(keycloakURL + "/admin/realms/" + realm + "/users/" + userId + "/reset-password", httpEntity);
        } catch (HttpClientErrorException.BadRequest exception) {
            keyCloakUsersRemoverService.removeKCUser(userId);
            String message = exception.getMessage().split(":")[4].split("\"")[0];
            throw new UserKeyCloakException(HttpStatus.BAD_REQUEST, UserKeyCloakException.INVALID_PASSWORD, new Object[]{message});
        }
    }
}
