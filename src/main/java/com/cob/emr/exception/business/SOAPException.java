package com.cob.emr.exception.business;

import org.springframework.http.HttpStatus;

public class SOAPException extends EMRException{
    public static final String CATEGORY_NOT_FOUND = Category.Business.value() + getPrefix() +"_00";
    public static final String FIELD_NOT_FOUND = Category.Business.value() + getPrefix() +"_01";

    public SOAPException(String code) {
        super(code);
    }

    public SOAPException(String code, Object[] parameters) {
        super(code, parameters);
    }

    public SOAPException(HttpStatus status, String code, Object[] parameters) {
        super(status, code, parameters);
    }

    protected static String getPrefix() {
        return "_soap";
    }
}
