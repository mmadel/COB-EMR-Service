package com.cob.emr.usecases.user.keycloak.http.creator;

import com.cob.emr.exception.business.UserException;
import com.cob.emr.usecases.AuthenticateKeyCloakAdminUseCase;
import com.cob.emr.usecases.user.keycloak.DeleteKeyCloakUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class CreateKCHTTPRequest {


    @Autowired
    AuthenticateKeyCloakAdminUseCase authenticateKeyCloakAdminUseCase;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    DeleteKeyCloakUserUseCase deleteKeyCloakUserUseCase;

    public void request(String body, String url) throws HttpServerErrorException.InternalServerError, HttpClientErrorException.BadRequest {
        HttpEntity<String> httpEntity = new HttpEntity<>(body, createHHTPHeader());
        restTemplate.put(url, httpEntity);

    }

    private HttpHeaders createHHTPHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        authenticateKeyCloakAdminUseCase.authenticate();
        headers.setBearerAuth(authenticateKeyCloakAdminUseCase.getAccessToken());
        return headers;
    }
}
