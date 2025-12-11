package com.cob.emr.ddd.infrastructure.persistence.mapper;

import com.cob.emr.ddd.domain.medicalnote.model.InitialEvaluation;
import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.NoteStatus;
import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.NoteType;
import com.cob.emr.ddd.domain.medicalnote.model.subjective.SubjectiveFindings;
import com.cob.emr.ddd.domain.medicalnote.model.objective.ObjectiveFindings;
import com.cob.emr.ddd.domain.medicalnote.model.assessment.ClinicalAssessment;
import com.cob.emr.ddd.domain.medicalnote.model.planofcare.PlanOfCare;
import com.cob.emr.ddd.domain.medicalnote.model.billing.BillingInformation;
import com.cob.emr.ddd.infrastructure.persistence.entity.MedicalNoteEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Bidirectional mapper between InitialEvaluation domain model and MedicalNoteEntity
 *
 * Responsibilities:
 * - Extract queryable fields from domain for database indexing
 * - Serialize/deserialize SOAP sections to/from JSON
 * - Maintain clean separation between domain and persistence
 */
@Component
public class InitialEvaluationEntityMapper {

    private final ObjectMapper objectMapper;

    public InitialEvaluationEntityMapper() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        // Configure to handle dates properly
        this.objectMapper.findAndRegisterModules();
    }

    // ==================== WRITE PATH: Domain → Entity ====================

    /**
     * Converts InitialEvaluation domain model to MedicalNoteEntity for persistence
     *
     * @param domain InitialEvaluation domain object
     * @return MedicalNoteEntity ready for database persistence
     */
    public MedicalNoteEntity toEntity(InitialEvaluation domain) {
        if (domain == null) {
            return null;
        }

        MedicalNoteEntity entity = MedicalNoteEntity.builder()
                // Core metadata
                .noteType(MedicalNoteEntity.NoteType.INITIAL_EVALUATION)
                .patientCaseId(domain.getPatientCaseId())
                .visitNumber(domain.getVisitNumber())
                .dateOfService(domain.getDateOfService())
                .status(mapDomainStatusToEntity(domain.getStatus()))
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedAt(LocalDateTime.now())

                // Extracted columns for query performance
                .painScale(extractPainScale(domain))
                .chiefComplaint(extractChiefComplaint(domain))
                .injuryOnsetDate(extractInjuryOnsetDate(domain))
                .workStatus(extractWorkStatus(domain))
                .complexityLevel(extractComplexityLevel(domain))
                .rehabPotential(extractRehabPotential(domain))
                .isPatientCompliant(extractPatientCompliance(domain))
                .totalBillingUnits(extractTotalBillingUnits(domain))
                .hasEvaluationCode(extractHasEvaluationCode(domain))

                // Full SOAP as JSON
                .subjectiveJson(domain.getSubjective())
                .objectiveJson(domain.getObjective())
                .assessmentJson(domain.getAssessment())
                .planOfCareJson(domain.getPlanOfCare())
                .billingJson(domain.getBilling())
                .build();

        return entity;
    }

    /**
     * Updates an existing entity with domain data (for updates)
     * Preserves id and creation metadata
     */
    public void updateEntity(MedicalNoteEntity entity, InitialEvaluation domain) {
        if (entity == null || domain == null) {
            return;
        }

        // Update metadata (don't change id, noteType, createdBy, createdAt)
        entity.setPatientCaseId(domain.getPatientCaseId());
        entity.setVisitNumber(domain.getVisitNumber());
        entity.setDateOfService(domain.getDateOfService());
        entity.setStatus(mapDomainStatusToEntity(domain.getStatus()));
        entity.setUpdatedAt(LocalDateTime.now());

        // Update extracted columns
        entity.setPainScale(extractPainScale(domain));
        entity.setChiefComplaint(extractChiefComplaint(domain));
        entity.setInjuryOnsetDate(extractInjuryOnsetDate(domain));
        entity.setWorkStatus(extractWorkStatus(domain));
        entity.setComplexityLevel(extractComplexityLevel(domain));
        entity.setRehabPotential(extractRehabPotential(domain));
        entity.setIsPatientCompliant(extractPatientCompliance(domain));
        entity.setTotalBillingUnits(extractTotalBillingUnits(domain));
        entity.setHasEvaluationCode(extractHasEvaluationCode(domain));

        // Update JSON
        entity.setSubjectiveJson(domain.getSubjective());
        entity.setObjectiveJson(domain.getObjective());
        entity.setAssessmentJson(domain.getAssessment());
        entity.setPlanOfCareJson(domain.getPlanOfCare());
        entity.setBillingJson(domain.getBilling());
    }

    // ==================== READ PATH: Entity → Domain ====================

    /**
     * Reconstructs InitialEvaluation domain model from MedicalNoteEntity
     *
     * @param entity MedicalNoteEntity from database
     * @return InitialEvaluation domain object
     */
    public InitialEvaluation toDomain(MedicalNoteEntity entity) {
        if (entity == null) {
            return null;
        }

        // Verify this is an Initial Evaluation
        if (!MedicalNoteEntity.NoteType.INITIAL_EVALUATION.equals(entity.getNoteType())) {
            throw new IllegalArgumentException(
                    "Cannot map non-Initial Evaluation entity. Found: " + entity.getNoteType()
            );
        }

        // Reconstruct domain object
        return new InitialEvaluation(
                entity.getPatientCaseId(),
                entity.getVisitNumber(),
                entity.getDateOfService(),
                mapEntityStatusToDomain(entity.getStatus()),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getSubjectiveJson(),
                entity.getObjectiveJson(),
                entity.getAssessmentJson(),
                entity.getPlanOfCareJson(),
                entity.getBillingJson()
        );
    }

    // ==================== Extracted Field Extractors ====================

    private Integer extractPainScale(InitialEvaluation domain) {
        return domain.getPainScale(); // Uses convenience method from InitialEvaluation
    }

    private String extractChiefComplaint(InitialEvaluation domain) {
        return domain.getChiefComplaint(); // Uses convenience method
    }

    private java.time.LocalDate extractInjuryOnsetDate(InitialEvaluation domain) {
        return domain.getInjuryOnsetDate(); // Uses convenience method
    }

    private String extractWorkStatus(InitialEvaluation domain) {
        return domain.getWorkStatus(); // Uses convenience method
    }

    private String extractComplexityLevel(InitialEvaluation domain) {
        return domain.getComplexityLevel(); // Uses convenience method
    }

    private String extractRehabPotential(InitialEvaluation domain) {
        return domain.getRehabPotential(); // Uses convenience method
    }

    private Boolean extractPatientCompliance(InitialEvaluation domain) {
        return domain.isPatientCompliant(); // Uses convenience method
    }

    private Integer extractTotalBillingUnits(InitialEvaluation domain) {
        return domain.getTotalBillingUnits(); // Uses convenience method
    }

    private Boolean extractHasEvaluationCode(InitialEvaluation domain) {
        if (domain.getBilling() == null) {
            return false;
        }
        return domain.getBilling().hasEvaluationCode();
    }

    // ==================== Status Mapping ====================

    private MedicalNoteEntity.NoteStatus mapDomainStatusToEntity(NoteStatus domainStatus) {
        if (domainStatus == null) {
            return MedicalNoteEntity.NoteStatus.DRAFT;
        }
        return MedicalNoteEntity.NoteStatus.valueOf(domainStatus.name());
    }

    private NoteStatus mapEntityStatusToDomain(MedicalNoteEntity.NoteStatus entityStatus) {
        if (entityStatus == null) {
            return NoteStatus.DRAFT;
        }
        return NoteStatus.valueOf(entityStatus.name());
    }
}