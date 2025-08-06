package com.cob.emr.enums;

public enum MedicalNoteStatus {
    Create("create"),
    Remove("remove"),
    Draft("draft"),
    Froward("Froward"),
    Finalized("Finalized");
    public final String label;
    MedicalNoteStatus(String label) {
        this.label = label;
    }
}
