# EMR Domain Model Generator for Windows
# Generates all Java domain model files from artifacts
# Usage: .\Generate-DomainModel.ps1
# Run from project root directory

$ErrorActionPreference = "Stop"

$BASE_DIR = "src\main\java"
$DOMAIN_BASE = "com\cob\emr\ddd\domain\medicalnote"



# Create base directories
$directories = @(
"$BASE_DIR\$DOMAIN_BASE\model",
"$BASE_DIR\$DOMAIN_BASE\model\valueobjects",
"$BASE_DIR\$DOMAIN_BASE\model\subjective",
"$BASE_DIR\$DOMAIN_BASE\model\objective",
"$BASE_DIR\$DOMAIN_BASE\model\assessment",
"$BASE_DIR\$DOMAIN_BASE\model\planofcare",
"$BASE_DIR\$DOMAIN_BASE\model\billing",
"$BASE_DIR\$DOMAIN_BASE\exception"
)

foreach ($dir in $directories) {
    if (!(Test-Path $dir)) {
        New-Item -ItemType Directory -Path $dir -Force | Out-Null
    }
}

# Helper function to create Java files
function Create-JavaFile {
    param (
        [string]$Package,
        [string]$FileName,
        [string]$Content
    )

    $filePath = Join-Path $BASE_DIR (Join-Path $Package $FileName)
    Set-Content -Path $filePath -Value $Content -Encoding UTF8
}



# NoteType.java
Create-JavaFile "$DOMAIN_BASE\model\valueobjects" "NoteType.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.valueobjects;

public enum NoteType {
    INITIAL_EVALUATION,
    DAILY_NOTE,
    PROGRESS_NOTE,
    FULL_DISCHARGE_NOTE
}
'@

# NoteStatus.java
Create-JavaFile "$DOMAIN_BASE\model\valueobjects" "NoteStatus.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.valueobjects;

public enum NoteStatus {
    DRAFT,
    FORWARDED,
    FINALIZED,
    REMOVED
}
'@

# VisitType.java
Create-JavaFile "$DOMAIN_BASE\model\valueobjects" "VisitType.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.valueobjects;

public enum VisitType {
    INITIAL,
    FOLLOW_UP,
    RE_EVALUATION
}
'@

# TreatmentSide.java
Create-JavaFile "$DOMAIN_BASE\model\valueobjects" "TreatmentSide.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.valueobjects;

public enum TreatmentSide {
    LEFT,
    RIGHT,
    BILATERAL,
    NOT_APPLICABLE
}
'@

# OnsetType.java
Create-JavaFile "$DOMAIN_BASE\model\valueobjects" "OnsetType.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.valueobjects;

public enum OnsetType {
    ACUTE_TRAUMATIC,
    ACUTE_NON_TRAUMATIC,
    CHRONIC,
    INSIDIOUS,
    EXACERBATION
}
'@

# WorkStatus.java
Create-JavaFile "$DOMAIN_BASE\model\valueobjects" "WorkStatus.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.valueobjects;

public enum WorkStatus {
    FULL_DUTY,
    LIGHT_DUTY,
    MODIFIED_DUTY,
    OFF_WORK,
    RETIRED,
    UNEMPLOYED
}
'@

# DutyLevel.java
Create-JavaFile "$DOMAIN_BASE\model\valueobjects" "DutyLevel.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.valueobjects;

public enum DutyLevel {
    SEDENTARY,
    LIGHT,
    MEDIUM,
    HEAVY,
    VERY_HEAVY
}
'@

# TobaccoUseStatus.java
Create-JavaFile "$DOMAIN_BASE\model\valueobjects" "TobaccoUseStatus.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.valueobjects;

public enum TobaccoUseStatus {
    NEVER_USED,
    CURRENT_USER,
    FORMER_USER
}
'@

# ActivityLevel.java
Create-JavaFile "$DOMAIN_BASE\model\valueobjects" "ActivityLevel.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.valueobjects;

public enum ActivityLevel {
    INDEPENDENT(0),
    MODIFIED_INDEPENDENT(1),
    SUPERVISION_REQUIRED(2),
    MINIMAL_ASSISTANCE(3),
    MODERATE_ASSISTANCE(4),
    MAXIMAL_ASSISTANCE(5),
    DEPENDENT(6),
    NOT_ASSESSED(0);

    private final int points;

    ActivityLevel(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }
}
'@

# SymptomPattern.java
Create-JavaFile "$DOMAIN_BASE\model\valueobjects" "SymptomPattern.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.valueobjects;

public enum SymptomPattern {
    CONSTANT,
    INTERMITTENT,
    PROGRESSIVE,
    IMPROVING,
    FLUCTUATING
}
'@

# ComplexityLevel.java
Create-JavaFile "$DOMAIN_BASE\model\valueobjects" "ComplexityLevel.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.valueobjects;

public enum ComplexityLevel {
    LOW,
    MODERATE,
    HIGH
}
'@

# GoalType.java
Create-JavaFile "$DOMAIN_BASE\model\valueobjects" "GoalType.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.valueobjects;

public enum GoalType {
    SHORT_TERM("Short Term"),
    LONG_TERM("Long Term");

    private final String displayName;

    GoalType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
'@

# TimePeriod.java
Create-JavaFile "$DOMAIN_BASE\model\valueobjects" "TimePeriod.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.valueobjects;

public enum TimePeriod {
    TWO_WEEK("2-week"),
    FOUR_WEEK("4-week"),
    SIX_WEEK("6-week"),
    EIGHT_WEEK("8-week"),
    CUSTOM("custom");

    private final String value;

    TimePeriod(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TimePeriod fromString(String value) {
        for (TimePeriod period : values()) {
            if (period.value.equals(value)) {
                return period;
            }
        }
        return CUSTOM;
    }
}
'@

# GoalStatus.java
Create-JavaFile "$DOMAIN_BASE\model\valueobjects" "GoalStatus.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.valueobjects;

public enum GoalStatus {
    IN_PROGRESS,
    MET,
    NOT_MET,
    REVISED
}
'@

# RehabPotentialRating.java
Create-JavaFile "$DOMAIN_BASE\model\valueobjects" "RehabPotentialRating.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.valueobjects;

public enum RehabPotentialRating {
    EXCELLENT("Excellent"),
    GOOD("Good"),
    FAIR("Fair"),
    POOR("Poor"),
    GUARDED("Guarded");

    private final String displayName;

    RehabPotentialRating(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static RehabPotentialRating fromString(String value) {
        if (value == null) return null;
        for (RehabPotentialRating rating : values()) {
            if (rating.displayName.equalsIgnoreCase(value)) {
                return rating;
            }
        }
        return null;
    }
}
'@

# ProblemStatus.java
Create-JavaFile "$DOMAIN_BASE\model\valueobjects" "ProblemStatus.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.valueobjects;

public enum ProblemStatus {
    ACTIVE,
    RESOLVED,
    IMPROVING
}
'@

# ModalityType.java
Create-JavaFile "$DOMAIN_BASE\model\valueobjects" "ModalityType.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.valueobjects;

public enum ModalityType {
    LASER,
    HOT_PACKS,
    CRYOTHERAPY,
    ELECTRICAL_STIMULATION,
    ULTRASOUND,
    WHIRLPOOL,
    DIATHERMY,
    MECHANICAL_TRACTION,
    BIOFEEDBACK,
    PARAFFIN_BATH,
    INFRARED_LIGHT,
    VASOPNEUMATIC,
    ULTRAVIOLET
}
'@

# ModalityPurpose.java
Create-JavaFile "$DOMAIN_BASE\model\valueobjects" "ModalityPurpose.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.valueobjects;

public enum ModalityPurpose {
    PAIN_RELIEF,
    INCREASE_BLOOD_FLOW,
    DECREASE_INFLAMMATION,
    IMPROVE_TISSUE_HEALING
}
'@

# ProcedureCategory.java
Create-JavaFile "$DOMAIN_BASE\model\valueobjects" "ProcedureCategory.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.valueobjects;

public enum ProcedureCategory {
    THERAPEUTIC_EXERCISES,
    THERAPEUTIC_ACTIVITY,
    GAIT_TRAINING,
    MANUAL_THERAPY,
    NEUROMUSCULAR_REHABILITATION,
    MASSAGE,
    AQUATIC_THERAPY,
    PATIENT_EDUCATION,
    SELF_CARE,
    SPLINTING_TAPING,
    IONTOPHORESIS,
    WOUND_CARE,
    VESTIBULAR_REHABILITATION,
    CARDIAC_REHABILITATION,
    GROUP_THERAPY
}
'@

# SpecialtyServiceType.java
Create-JavaFile "$DOMAIN_BASE\model\valueobjects" "SpecialtyServiceType.java" @'
package com.cob.emr.ddd.domain.medicalnote.model.valueobjects;

public enum SpecialtyServiceType {
    ORTHOTIC_FABRICATION,
    TENS_FITTING,
    ACUPUNCTURE,
    OTHER
}
'@


# MedicalNote.java
Create-JavaFile "$DOMAIN_BASE\model" "MedicalNote.java" @'
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
'@


# DomainException.java
Create-JavaFile "$DOMAIN_BASE\exception" "DomainException.java" @'
package com.cob.emr.ddd.domain.medicalnote.exception;

public class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }

    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
'@

# InvalidNoteException.java
Create-JavaFile "$DOMAIN_BASE\exception" "InvalidNoteException.java" @'
package com.cob.emr.ddd.domain.medicalnote.exception;

public class InvalidNoteException extends DomainException {
    public InvalidNoteException(String message) {
        super(message);
    }
}
'@

# InvalidVisitNumberException.java
Create-JavaFile "$DOMAIN_BASE\exception" "InvalidVisitNumberException.java" @'
package com.cob.emr.ddd.domain.medicalnote.exception;

public class InvalidVisitNumberException extends DomainException {
    private final int attemptedVisitNumber;
    private final int expectedVisitNumber;

    public InvalidVisitNumberException(int attemptedVisitNumber, int expectedVisitNumber) {
        super(String.format("Invalid visit number. Attempted: %d, Expected: %d",
            attemptedVisitNumber, expectedVisitNumber));
        this.attemptedVisitNumber = attemptedVisitNumber;
        this.expectedVisitNumber = expectedVisitNumber;
    }

    public int getAttemptedVisitNumber() {
        return attemptedVisitNumber;
    }

    public int getExpectedVisitNumber() {
        return expectedVisitNumber;
    }
}
'@

# BillingValidationException.java
Create-JavaFile "$DOMAIN_BASE\exception" "BillingValidationException.java" @'
package com.cob.emr.ddd.domain.medicalnote.exception;

public class BillingValidationException extends DomainException {
    public BillingValidationException(String message) {
        super(message);
    }
}
'@

# NoteNotFoundException.java
Create-JavaFile "$DOMAIN_BASE\exception" "NoteNotFoundException.java" @'
package com.cob.emr.ddd.domain.medicalnote.exception;

public class NoteNotFoundException extends DomainException {
    private final Long noteId;

    public NoteNotFoundException(Long noteId) {
        super(String.format("Medical note not found with ID: %d", noteId));
        this.noteId = noteId;
    }

    public Long getNoteId() {
        return noteId;
    }
}
'@

# MissingPrerequisiteNoteException.java
Create-JavaFile "$DOMAIN_BASE\exception" "MissingPrerequisiteNoteException.java" @'
package com.cob.emr.ddd.domain.medicalnote.exception;

public class MissingPrerequisiteNoteException extends DomainException {
    public MissingPrerequisiteNoteException(String message) {
        super(message);
    }
}
'@