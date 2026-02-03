package com.cob.emr.exception.business;

import org.springframework.http.HttpStatus;

public class PatientCaseException extends EMRException {
    public static final String CASE_ASSIGN_TO_APPOINTMENT = Category.Business.value() + getPrefix() +"_00";
    public static final String CASE_NOT_FOUND = Category.Business.value() + getPrefix() + "_01";
    public PatientCaseException(String code) {
        super(code);
    }

    public PatientCaseException(String code, Object[] parameters) {
        super(code, parameters);
    }

    public PatientCaseException(HttpStatus status, String code, Object[] parameters) {
        super(status, code, parameters);
    }

    protected static String getPrefix() {
        return "_patientcase";
    }
}
