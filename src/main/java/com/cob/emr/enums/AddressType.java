package com.cob.emr.enums;

public enum AddressType {
    HOME("Home"),
    WORK("Work"),
    OTHER("Other");

    public final String label;

    AddressType(String label) {
        this.label = label;
    }
}
