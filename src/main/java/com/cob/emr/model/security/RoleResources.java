package com.cob.emr.model.security;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RoleResources {
    private String roleName;
    private String[] authorities;
}
