package com.cob.emr.usecases.user.keycloak.creator;

import com.cob.emr.exception.business.UserException;
import com.cob.emr.model.user.keycloak.KeyCloakUser;
import com.cob.emr.security.DecryptPassword;
import com.cob.emr.usecases.AuthenticateKeyCloakAdminUseCase;
import com.cob.emr.usecases.user.keycloak.DeleteKeyCloakUserUseCase;
import com.cob.emr.usecases.user.keycloak.http.creator.CreateKCHTTPRequest;
import com.google.gson.Gson;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
public class CreateUserCredentialsStep extends KeycloakUserCreationStepper {
    private final static String RESET_PASSWORD = "reset-password";
    private final static String EXECUTE_ACTIONS_EMAIL = "execute-actions-email";
    @Autowired
    AuthenticateKeyCloakAdminUseCase authenticateKeyCloakAdminUseCase;
    @Autowired
    DeleteKeyCloakUserUseCase deleteKeyCloakUserUseCase;
    @Autowired
    DecryptPassword decryptPassword;
    @Autowired
    CreateKCHTTPRequest createKCHTTPRequest;
    @Value("${kc.url}")
    private String keycloakURL;
    @Value("${kc.realm}")
    private String realm;
    private static final String KC_ADMIN_RELATIVE_PATH = "/admin/realms/";
    private static final String KC_USER_RESOURCE = "/users/";
    private static final String KC_PASSWORD_RESET_PASSWORD_ACTION = "/reset-password";
    private static final String KC_PASSWORD_EXECUTE_EMAIL_ACTION = "/execute-actions-email";

    @Override
    public boolean create(KeyCloakUser keyCloakUser) throws UserException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(true);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(decryptPassword.decrypt(keyCloakUser.getPassword()));
        Gson gson = new Gson();
        String url = keycloakURL + KC_ADMIN_RELATIVE_PATH + realm + KC_USER_RESOURCE + keyCloakUser.getUuid() + KC_PASSWORD_RESET_PASSWORD_ACTION;
        createKCHTTPRequest.request(gson.toJson(credential), url);
        try {
            createKCHTTPRequest.request(gson.toJson(credential), url);
        } catch (HttpServerErrorException.InternalServerError exception) {
            deleteKeyCloakUserUseCase.delete(keyCloakUser.getUserId());
            String message = exception.getMessage().split("errorMessage\":")[1].replace("}", "")
                    .replace("\"", "");
            throw new UserException(HttpStatus.BAD_REQUEST, UserException.INVALID_PASSWORD, new Object[]{message});
        } catch (HttpClientErrorException.BadRequest exception) {
            deleteKeyCloakUserUseCase.delete(keyCloakUser.getUserId());
            String message = exception.getMessage().split("error\":")[1].replace("}", "")
                    .replace("\"", "");
            throw new UserException(HttpStatus.BAD_REQUEST, UserException.INVALID_PASSWORD, new Object[]{message});

        }
        return nextStep(keyCloakUser);
    }
}
