package com.cob.emr.exception.business;

import org.springframework.http.HttpStatus;

public class UserKeyCloakException extends EMRException{
    public static final String INVALID_PASSWORD = Category.Business.value() + getPrefix() +"_01";;
    public UserKeyCloakException(String code) {
        super(code);
    }

    public UserKeyCloakException(String code, Object[] parameters) {
        super(code, parameters);
    }

    public UserKeyCloakException(HttpStatus status, String code, Object[] parameters) {
        super(status, code, parameters);
    }

    protected static String getPrefix() {
        return "_keycloak";
    }
}
