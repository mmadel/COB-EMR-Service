package com.cob.emr.enums;

public enum AppointmentStatus {
    Created("Created"),
    Confirmed("Confirmed"),
    CheckIn("Check In"),
    Checkout("Check Out"),
    Cancel("Cancel"),
    NoShow("No Show");
    public final String label;

    AppointmentStatus(String label) {
        this.label = label;
    }
    @Override
    public String toString() {
        return this.label;
    }
}
