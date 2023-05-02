package com.cob.emr.enums;

public enum MaritalStatus {
    Single("Single"),
    Married("Married"),
    Widowed("Widowed"),
    Divorced("Divorced");

    public final String label;

     MaritalStatus(String label) {
        this.label = label;
    }
}
