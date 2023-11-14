package com.cob.emr.usecases.user.keycloak;


import com.cob.emr.model.user.keycloak.Credentials;
import com.cob.emr.model.user.keycloak.Tokens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthenticationService {
    @Value("${kc.auth.url}")
    private String tokenUrl;
    @Value("${kc.client.id}")
    private String clientId;
    @Value("${kc.client.secret}")
    private String clientSecret;

    @Autowired
    RestTemplate restTemplate;
    public Tokens getTokens(Credentials credentials){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", credentials.getUserName());
        map.add("password", credentials.getPassword());
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("grant_type", "password");
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);
        ResponseEntity<Tokens> response = restTemplate.postForEntity(tokenUrl, httpEntity, Tokens.class);

        return response.getBody();
    }
}
