package com.cob.emr.exception.business;

import org.springframework.http.HttpStatus;

public class SignatureTokenException extends EMRException {
    public static final String TOKEN_MALFORMED = Category.Business.value() + getPrefix() + "_00";
    public static final String TOKEN_NOT_FOUND = Category.Business.value() + getPrefix() + "_01";
    public static final String TOKEN_EXPIRED = Category.Business.value() + getPrefix() + "_02";
    public static final String OTP_NOT_FOUND = Category.Business.value() + getPrefix() + "_03";
    public static final String OTP_EXPIRED = Category.Business.value() + getPrefix() + "_04";
    public static final String OTP_INVALID = Category.Business.value() + getPrefix() + "_05";
    public static final String CONSENT_NOT_ACKNOWLEDGED = Category.Business.value() + getPrefix() + "_06";
    public static final String CONSENT_VERSION_NOT_FOUND = Category.Business.value() + getPrefix() + "_07";
    public static final String CONSENT_TEXT_MISMATCH = Category.Business.value() + getPrefix() + "_08";
    public static final String CONSENT_NOT_READ = Category.Business.value() + getPrefix() + "_09";
    public static final String SIGNATURE_DATA_MISSING = Category.Business.value() + getPrefix() + "_10";
    public static final String OTP_NOT_VERIFIED = Category.Business.value() + getPrefix() + "_11";

    public SignatureTokenException(String code) {
        super(code);
    }

    public SignatureTokenException(String code, Object[] parameters) {
        super(code, parameters);
    }

    public SignatureTokenException(HttpStatus status, String code, Object[] parameters) {
        super(status, code, parameters);
    }

    protected static String getPrefix() {
        return "_signature";
    }
}
