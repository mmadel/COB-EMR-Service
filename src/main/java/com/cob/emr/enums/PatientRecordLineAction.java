package com.cob.emr.enums;

public enum PatientRecordLineAction {
    Complete("Complete"),
    Remove("Remove"),
    Add_Addendum("Add Addendum"),
    View_PDF("View Pdf"),
    View_Reason("View Reason");

    public String label;

    PatientRecordLineAction(String label) {
        this.label = label;
    }
}
