package com.cob.emr.ddd.adapter.dto.request;

import com.cob.emr.model.medical.note.types.initial.evaluation.assessment.Assessment;
import com.cob.emr.model.medical.note.types.initial.evaluation.billing.Billing;
import com.cob.emr.model.medical.note.types.initial.evaluation.plan.PlanOfCare;
import com.cob.emr.model.medical.note.types.initial.evaluation.subjective.Subjective;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Request DTO for Initial Evaluation
 * Maps directly to Angular JSON structure
 *
 * This DTO receives the complete Initial Evaluation data from the frontend.
 * It uses the existing DTO classes (Subjective, Assessment, PlanOfCare, Billing)
 * that match the Angular structure.
 */
@Data
public class InitialEvaluationRequest {

    /**
     * The SOAP components
     */
    private Subjective subjective;

    private Object objective;  // TODO: Create ObjectiveRequest DTO when needed

    private Assessment assessment;

    @JsonProperty("planOfCare")
    private PlanOfCare planOfCare;

    private Billing billing;

    /**
     * Top-level metadata
     */
    private Long patientCaseId;

    private String noteType;  // Should be "INITIAL_EVALUATION"

    private Integer visitNumber;

    private String dateOfService;

    private String status;  // DRAFT, FINAL, etc.

    private String createdBy;

    private String createdAt;
}
