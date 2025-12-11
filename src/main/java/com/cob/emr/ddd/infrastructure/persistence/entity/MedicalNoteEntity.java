package com.cob.emr.ddd.infrastructure.persistence.entity;

import com.cob.emr.ddd.domain.medicalnote.model.assessment.ClinicalAssessment;
import com.cob.emr.ddd.domain.medicalnote.model.billing.BillingInformation;
import com.cob.emr.ddd.domain.medicalnote.model.objective.ObjectiveFindings;
import com.cob.emr.ddd.domain.medicalnote.model.planofcare.PlanOfCare;
import com.cob.emr.ddd.domain.medicalnote.model.subjective.SubjectiveFindings;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;


import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Single Table Inheritance entity for all Medical Note types
 * Uses hybrid approach: Extracted columns for queries + Full JSON for complete data
 *
 * Supports: INITIAL_EVALUATION, DAILY_NOTE, PROGRESS_NOTE, DISCHARGE_NOTE
 */
@Entity
@Table(name = "medical_note_new", indexes = {
        @Index(name = "idx_patient_case", columnList = "patient_case_id"),
        @Index(name = "idx_date_of_service", columnList = "date_of_service"),
        @Index(name = "idx_note_type", columnList = "note_type"),
        @Index(name = "idx_status", columnList = "status"),
        @Index(name = "idx_visit_number", columnList = "patient_case_id, visit_number")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalNoteEntity {

    // ==================== Primary Key ====================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ==================== Core Metadata ====================

    @Column(name = "note_type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private NoteType noteType;

    @Column(name = "patient_case_id", nullable = false)
    private Long patientCaseId;

    @Column(name = "visit_number", nullable = false)
    private Integer visitNumber;

    @Column(name = "date_of_service", nullable = false)
    private LocalDate dateOfService;

    @Column(name = "status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private NoteStatus status;

    @Column(name = "created_by", nullable = false, length = 100)
    private String createdBy;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ==================== Extracted Columns for Query Performance ====================
    // These are denormalized from JSON for fast filtering/sorting
    // Nullable because some note types may not have all fields

    /**
     * Pain scale (0-10) - Extracted from subjective.painAssessment.painScale
     * Used for: Trending, reporting, clinical decision support
     */
    @Column(name = "pain_scale")
    private Integer painScale;

    /**
     * Chief complaint - Extracted from subjective.chiefComplaint.primaryConcern
     * Used for: Quick patient overview, reporting
     */
    @Column(name = "chief_complaint", length = 500)
    private String chiefComplaint;

    /**
     * Injury onset date - Extracted from subjective.injuryHistory.injuryOnsetDate
     * Used for: Calculating injury duration, reporting
     */
    @Column(name = "injury_onset_date")
    private LocalDate injuryOnsetDate;

    /**
     * Work status - Extracted from subjective.medicalHistory.occupationalHistory.workStatus
     * Used for: Work comp reporting, return-to-work tracking
     */
    @Column(name = "work_status", length = 50)
    private String workStatus;

    /**
     * Complexity level - Extracted from assessment.patientPresentation.complexityLevel
     * Used for: Resource allocation, billing validation
     */
    @Column(name = "complexity_level", length = 20)
    private String complexityLevel;

    /**
     * Rehabilitation potential - Extracted from assessment.rehabilitationPotential.rating
     * Used for: Treatment planning, discharge planning
     */
    @Column(name = "rehab_potential", length = 20)
    private String rehabPotential;

    /**
     * Patient compliance - Extracted from assessment.patientCompliance.isCompliant
     * Used for: Outcome tracking, discharge decisions
     */
    @Column(name = "is_patient_compliant")
    private Boolean isPatientCompliant;

    /**
     * Total billing units - Extracted from billing.calculateTotalUnits()
     * Used for: Billing reports, productivity tracking
     */
    @Column(name = "total_billing_units")
    private Integer totalBillingUnits;

    /**
     * Has evaluation code (97161-97163) - Extracted from billing
     * Used for: Billing validation, Initial Eval verification
     */
    @Column(name = "has_evaluation_code")
    private Boolean hasEvaluationCode;

    // ==================== Full SOAP Components as JSON ====================
    // Complete data preserved for document integrity, printing, export
    // MySQL 8+ JSON type for better performance and validation

    /**
     * Complete subjective section as JSON
     * Contains: examinationContext, chiefComplaint, injuryHistory, painAssessment,
     *           medicalHistory, functionalAssessment
     */
    @Column(name = "subjective_json", columnDefinition = "json")
    @Type(type = "json")
    private SubjectiveFindings subjectiveJson;

    /**
     * Complete objective section as JSON
     * Contains: precautions, preTreatmentFindings, postTreatmentFindings,
     *           clinicalObservations, performedInterventions, instructions
     */
    @Column(name = "objective_json", columnDefinition = "JSON")
    @Type(type = "json")
    private ObjectiveFindings objectiveJson;

    /**
     * Complete assessment section as JSON
     * Contains: patientPresentation, problemList, goals, diagnosis,
     *           rehabilitationPotential, patientCompliance, contraindications,
     *           consentToCare, patientEducation
     */
    @Column(name = "assessment_json", columnDefinition = "JSON")
    @Type(type = "json")
    private ClinicalAssessment assessmentJson;

    /**
     * Complete plan of care section as JSON
     * Contains: treatmentPlan, modalities, procedures, specialties,
     *           instructions, requiresPhysicianSignature
     */
    @Column(name = "plan_of_care_json", columnDefinition = "JSON")
    @Type(type = "json")
    private PlanOfCare planOfCareJson;

    /**
     * Complete billing section as JSON
     * Contains: timedCodes, untimedCodes, supplies, casts, braces,
     *           splintsOrthotics, respiratory, nerveConduction
     */
    @Column(name = "billing_json", columnDefinition = "JSON")
    @Type(type = "json")
    private BillingInformation billingJson;

    // ==================== Lifecycle Callbacks ====================

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // ==================== Enums ====================

    public enum NoteType {
        INITIAL_EVALUATION,
        DAILY_NOTE,
        PROGRESS_NOTE,
        DISCHARGE_NOTE,
        RE_EVALUATION
    }

    public enum NoteStatus {
        DRAFT,
        FINAL,
        AMENDED,
        DELETED
    }

    // ==================== Helper Methods ====================

    /**
     * Check if this is an Initial Evaluation
     */
    public boolean isInitialEvaluation() {
        return NoteType.INITIAL_EVALUATION.equals(this.noteType);
    }

    /**
     * Check if note is finalized
     */
    public boolean isFinal() {
        return NoteStatus.FINAL.equals(this.status);
    }

    /**
     * Check if note can be edited
     */
    public boolean isEditable() {
        return NoteStatus.DRAFT.equals(this.status);
    }
}

