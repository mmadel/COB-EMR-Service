package com.cob.emr.enums;

public enum MedicalNoteType {
    INITIAL_EVALUATION("Initial Evaluation"),
    DAILY_NOTE("Daily Note"),
    PROGRESS_NOTE("Progress Note"),
    DISCHARGE_NOTE("Discharge Note");
    public final String label;
    MedicalNoteType(String label) {
        this.label = label;
    }
    @Override
    public String toString() {
        return this.label;
    }
}