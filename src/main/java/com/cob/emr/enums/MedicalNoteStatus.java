package com.cob.emr.enums;

public enum MedicalNoteStatus {
    Create("create"),
    Draft("draft"),
    Froward("Froward"),
    Finalized("Finalized");
    public final String label;
    MedicalNoteStatus(String label) {
        this.label = label;
    }
}
