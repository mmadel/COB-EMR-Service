package com.cob.emr.exception.business;

import org.springframework.http.HttpStatus;

public class PatientException  extends EMRException {
    public static final String PATIENT_NOTFOUND = Category.Business.value() + getPrefix() +"_00";
    public PatientException(String code) {
        super(code);
    }

    public PatientException(String code, Object[] parameters) {
        super(code, parameters);
    }

    public PatientException(HttpStatus status, String code, Object[] parameters) {
        super(status, code, parameters);
    }

    protected static String getPrefix() {
        return "_patient";
    }
}
