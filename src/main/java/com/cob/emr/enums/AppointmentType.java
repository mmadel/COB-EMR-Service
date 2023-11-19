package com.cob.emr.enums;

public enum AppointmentType {
    OK("#9aff00"),
    Error("#9aff00"),
    Warning("#ffd500"),
    Follow("#fjf44");

    public final String label;
    AppointmentType(String label) {
        this.label = label;
    }
    @Override
    public String toString() {
        return this.label;
    }
}
