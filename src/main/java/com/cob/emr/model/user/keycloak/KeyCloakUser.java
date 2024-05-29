
package com.cob.emr.model.user.keycloak;

import lombok.Builder;
import lombok.Data;
import org.keycloak.admin.client.resource.RealmResource;

import java.util.List;

@Data
@Builder
public class KeyCloakUser {
    private String userId;

    private String userName;
    private String firstName;
    private String lastName;

    private String password;
    private String email;
    private String address ;
    private List<String> roles;
    private String uuid;

}
