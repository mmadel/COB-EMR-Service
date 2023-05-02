package com.cob.emr.enums;

public enum IdType {
    SSN("ssn"),
    DRIVER_LICENCE("Drive Licence"),
    OTHER("Other");

    public final String label;

    IdType(String label) {
        this.label = label;
    }
}
