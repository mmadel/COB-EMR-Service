package com.cob.emr.model.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class SecurityAuthority {
    private String roleName;
    private String resource;
    private List<HttpMethod> availableMethods = new ArrayList<>();
}
