package com.cob.emr.enums;

public enum AppointmentStatus {
    Created("created"),
    Confirmed("confirmed"),
    CheckIn("check-in"),
    Checkout("check-out"),
    Cancel("cancel"),
    NoShow("no-show");
    public final String label;

    AppointmentStatus(String label) {
        this.label = label;
    }
    @Override
    public String toString() {
        return this.label;
    }
}
