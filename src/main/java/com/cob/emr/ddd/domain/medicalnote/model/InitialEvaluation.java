package com.cob.emr.ddd.domain.medicalnote.model;

import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.NoteStatus;
import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.NoteType;
import com.cob.emr.ddd.domain.medicalnote.model.subjective.SubjectiveFindings;
import com.cob.emr.ddd.domain.medicalnote.model.objective.ObjectiveFindings;
import com.cob.emr.ddd.domain.medicalnote.model.assessment.ClinicalAssessment;
import com.cob.emr.ddd.domain.medicalnote.model.planofcare.PlanOfCare;
import com.cob.emr.ddd.domain.medicalnote.model.billing.BillingInformation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class InitialEvaluation extends MedicalNote {

    private final SubjectiveFindings subjective;
    private final ObjectiveFindings objective;
    private final ClinicalAssessment assessment;
    private final PlanOfCare planOfCare;
    private final BillingInformation billing;

    public InitialEvaluation(Long patientCaseId,
                             int visitNumber,
                             LocalDate dateOfService,
                             NoteStatus status,
                             String createdBy,
                             LocalDateTime createdAt,
                             SubjectiveFindings subjective,
                             ObjectiveFindings objective,
                             ClinicalAssessment assessment,
                             PlanOfCare planOfCare,
                             BillingInformation billing) {
        super(patientCaseId, NoteType.INITIAL_EVALUATION, visitNumber,
                dateOfService, status, createdBy, createdAt);
        this.subjective = subjective;
        this.objective = objective;
        this.assessment = assessment;
        this.planOfCare = planOfCare;
        this.billing = billing;
    }

    @Override
    public void validate() {
        super.validate();

        if (this.getVisitNumber() != 1) {
            throw new IllegalStateException("Initial Evaluation must be visit number 1");
        }

        if (subjective == null) {
            throw new IllegalStateException("Initial Evaluation must have Subjective findings");
        }

        if (assessment == null) {
            throw new IllegalStateException("Initial Evaluation must have Assessment");
        }

        if (planOfCare == null) {
            throw new IllegalStateException("Initial Evaluation must have Plan of Care");
        }

        if (billing == null) {
            throw new IllegalStateException("Initial Evaluation must have Billing information");
        }

        subjective.validate();
        assessment.validate();
        planOfCare.validate();
        billing.validate();

        if (!billing.hasEvaluationCode()) {
            throw new IllegalStateException("Initial Evaluation must have evaluation billing code (97161-97163)");
        }
    }

    @Override
    public boolean canFinalize() {
        return super.canFinalize()
                && subjective != null
                && assessment != null
                && assessment.hasGoals()
                && planOfCare != null
                && billing != null
                && billing.hasEvaluationCode();
    }

    public ObjectiveFindings deriveObjectiveForDailyNote() {
        if (billing == null) {
            return null;
        }
        return billing.toObjectiveFindings();
    }

    public String getChiefComplaint() {
        return subjective != null && subjective.getChiefComplaint() != null
                ? subjective.getChiefComplaint().getPrimaryConcern()
                : null;
    }

    public Integer getPainScale() {
        return subjective != null && subjective.getPainAssessment() != null
                ? subjective.getPainAssessment().getPainScale()
                : null;
    }

    public String getRehabPotential() {
        return assessment != null && assessment.getRehabilitationPotential() != null
                ? assessment.getRehabilitationPotential().getRating().name()
                : null;
    }

    public String getWorkStatus() {
        return subjective != null
                && subjective.getMedicalHistory() != null
                && subjective.getMedicalHistory().getOccupationalHistory() != null
                && subjective.getMedicalHistory().getOccupationalHistory().getWorkStatus() != null
                ? subjective.getMedicalHistory().getOccupationalHistory().getWorkStatus().name()
                : null;
    }

    public String getComplexityLevel() {
        return assessment != null && assessment.getPatientPresentation() != null
                ? assessment.getPatientPresentation().getComplexityLevel().name()
                : null;
    }

    public Boolean isPatientCompliant() {
        return assessment != null && assessment.getPatientCompliance() != null
                ? assessment.getPatientCompliance().isCompliant()
                : null;
    }

    public LocalDate getInjuryOnsetDate() {
        return subjective != null
                && subjective.getInjuryHistory() != null
                ? subjective.getInjuryHistory().getInjuryOnsetDate()
                : null;
    }

    public Integer getTotalBillingUnits() {
        return billing != null ? billing.calculateTotalUnits() : null;
    }
}
