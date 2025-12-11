package com.cob.emr.ddd.domain.medicalnote.model;

import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.NoteStatus;
import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.NoteType;
import lombok.Getter;
import lombok.ToString;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Abstract Base Class for all Medical Notes
 * Aggregate Root
 */
@Getter
@ToString
public abstract class MedicalNote {

    private final Long patientCaseId;
    private final NoteType noteType;
    private final int visitNumber;
    private final LocalDate dateOfService;
    private final NoteStatus status;
    private final String createdBy;
    private final LocalDateTime createdAt;

    protected MedicalNote(Long patientCaseId,
                          NoteType noteType,
                          int visitNumber,
                          LocalDate dateOfService,
                          NoteStatus status,
                          String createdBy,
                          LocalDateTime createdAt) {
        this.patientCaseId = patientCaseId;
        this.noteType = noteType;
        this.visitNumber = visitNumber;
        this.dateOfService = dateOfService;
        this.status = status;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    /**
     * Template method for validation
     */
    public void validate() {
        if (patientCaseId == null) {
            throw new IllegalStateException("Patient case ID is required");
        }
        if (visitNumber < 1) {
            throw new IllegalStateException("Visit number must be positive");
        }
        if (dateOfService == null) {
            throw new IllegalStateException("Date of service is required");
        }
        if (createdBy == null || createdBy.trim().isEmpty()) {
            throw new IllegalStateException("Created by is required");
        }
    }

    public boolean canFinalize() {
        return status == NoteStatus.DRAFT;
    }

    public boolean canEdit() {
        return status == NoteStatus.DRAFT || status == NoteStatus.FORWARDED;
    }

    public boolean isFinalized() {
        return status == NoteStatus.FINALIZED;
    }
}