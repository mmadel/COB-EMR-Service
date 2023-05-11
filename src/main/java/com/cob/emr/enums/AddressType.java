package com.cob.emr.enums;

public enum AddressType {
    Home("Home"),
    Work("Work"),
    Other("Other");

    public final String label;

    AddressType(String label) {
        this.label = label;
    }
}
