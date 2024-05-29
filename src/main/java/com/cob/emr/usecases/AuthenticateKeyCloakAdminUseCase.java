package com.cob.emr.usecases;

import com.cob.emr.model.security.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticateKeyCloakAdminUseCase {
    @Value("${kc.auth.url}")
    private String tokenUrl;
    @Value("${kc.client.id}")
    private String clientId;
    @Value("${kc.client.secret}")
    private String clientSecret;
    @Value("${kc.administrator.username}")
    private String userName;
    @Value("${kc.administrator.password}")
    private String password;
    @Autowired
    RestTemplate restTemplate;

    private String accessToken;
    public void authenticate() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", userName);
        map.add("password", password);
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("grant_type", "password");
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);
        ResponseEntity<UserToken> response = restTemplate.postForEntity(tokenUrl, httpEntity, UserToken.class);
        accessToken = response.getBody().getAccess_token();
    }
    public String getAccessToken(){
        return accessToken;
    }
}
