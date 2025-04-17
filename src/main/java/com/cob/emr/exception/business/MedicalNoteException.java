package com.cob.emr.exception.business;

import org.springframework.http.HttpStatus;

public class MedicalNoteException extends EMRException{
    public static final String FINALIZE_INIT_EXAM = Category.Business.value() + getPrefix() +"_00";
    public static final String CORRUPTED_FINALIZER = Category.Business.value() + getPrefix() +"_01";

    public MedicalNoteException(String code) {
        super(code);
    }

    public MedicalNoteException(String code, Object[] parameters) {
        super(code, parameters);
    }

    public MedicalNoteException(HttpStatus status, String code, Object[] parameters) {
        super(status, code, parameters);
    }

    protected static String getPrefix() {
        return "_medicalnote";
    }
}
