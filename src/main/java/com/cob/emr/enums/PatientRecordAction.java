package com.cob.emr.enums;

public enum PatientRecordAction {
    Initial_Examination("Add Initial Examination"),
    Daily_Note("Add Daily Note"),
    Progress_Note("Progress Note"),
    Quick_Discharge("Quick Discharge"),
    Discharge("Discharge"),
    Case_Note("Case Note");

     final String value;

    PatientRecordAction(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
