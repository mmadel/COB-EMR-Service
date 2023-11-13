package com.cob.emr.exception.business;

import org.springframework.http.HttpStatus;

public class OrganizationException extends EMRException{
    public static final String ORGANIZATION_NOT_FOUND = Category.Business.value() + getPrefix() +"_00";

    public OrganizationException(String code) {
        super(code);
    }

    public OrganizationException(String code, Object[] parameters) {
        super(code, parameters);
    }

    public OrganizationException(HttpStatus status, String code, Object[] parameters) {
        super(status, code, parameters);
    }

    protected static String getPrefix() {
        return "_organization";
    }
}
