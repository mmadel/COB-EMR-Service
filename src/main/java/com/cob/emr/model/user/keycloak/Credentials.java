package com.cob.emr.model.user.keycloak;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Credentials {
    private String userName;
    private String password;
}
