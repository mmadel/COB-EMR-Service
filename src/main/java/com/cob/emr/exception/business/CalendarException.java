package com.cob.emr.exception.business;

import org.springframework.http.HttpStatus;

public class CalendarException extends EMRException {
    public static final String CALENDAR_HAS_APPOINTMENTS = Category.Business.value() + getPrefix() + "_00";

    public CalendarException(String code) {
        super(code);
    }

    public CalendarException(String code, Object[] parameters) {
        super(code, parameters);
    }

    public CalendarException(HttpStatus status, String code, Object[] parameters) {
        super(status, code, parameters);
    }

    protected static String getPrefix() {
        return "_calendar";
    }
}
