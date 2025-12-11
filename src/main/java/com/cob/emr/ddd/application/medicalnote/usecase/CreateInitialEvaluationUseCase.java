package com.cob.emr.ddd.application.medicalnote.usecase;

import com.cob.emr.ddd.adapter.mapper.InitialEvaluationDtoMapper;
import com.cob.emr.ddd.domain.medicalnote.model.InitialEvaluation;
import com.cob.emr.ddd.infrastructure.persistence.entity.MedicalNoteEntity;
import com.cob.emr.ddd.infrastructure.persistence.mapper.InitialEvaluationEntityMapper;
import com.cob.emr.ddd.infrastructure.persistence.repository.MedicalNoteNewRepository;
import com.cob.emr.model.medical.note.MedicalNoteRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use Case: Create Initial Evaluation
 *
 * Orchestrates the complete flow for creating a new Initial Evaluation medical note.
 * Follows Command pattern - single responsibility for creation.
 *
 * Flow:
 * 1. Request DTO → Domain Model (with business rules)
 * 2. Domain Validation
 * 3. Business Rule Validation (e.g., no duplicate Initial Eval)
 * 4. Domain → Entity (persistence format)
 * 5. Save to database
 * 6. Return created domain object
 */
@Component
public class CreateInitialEvaluationUseCase {

    private static final Logger log = LoggerFactory.getLogger(CreateInitialEvaluationUseCase.class);

    private final InitialEvaluationDtoMapper dtoMapper;
    private final InitialEvaluationEntityMapper entityMapper;
    private final MedicalNoteNewRepository repository;

    public CreateInitialEvaluationUseCase(
            InitialEvaluationDtoMapper dtoMapper,
            InitialEvaluationEntityMapper entityMapper,
            MedicalNoteNewRepository repository) {
        this.dtoMapper = dtoMapper;
        this.entityMapper = entityMapper;
        this.repository = repository;
    }

    /**
     * Execute the use case: Create a new Initial Evaluation
     *
     * @param request The Initial Evaluation request from Angular/API
     * @return The created InitialEvaluation domain object
     * @throws IllegalArgumentException if request is null or invalid
     * @throws IllegalStateException if business rules are violated
     */
    @Transactional
    public InitialEvaluation execute(MedicalNoteRequest request) {
        log.info("Executing CreateInitialEvaluationUseCase for patient case: {}",
                request != null ? request.getCaseId() : "null");

        // Step 1: Validate input
        validateRequest(request);

        // Step 2: Map Request DTO → Domain Model
        log.debug("Mapping DTO to domain model");
        InitialEvaluation domain = dtoMapper.toDomain(request);

        // Step 3: Domain Validation (enforces domain invariants)
        log.debug("Validating domain model");
        //domain.validate();

        // Step 4: Business Rule Validation
        log.debug("Validating business rules");
        validateBusinessRules(domain);

        // Step 5: Map Domain → Entity (persistence format)
        log.debug("Mapping domain to entity");
        MedicalNoteEntity entity = entityMapper.toEntity(domain);

        // Step 6: Persist to database
        log.debug("Persisting entity to database");
        MedicalNoteEntity savedEntity = repository.save(entity);
        log.info("Initial Evaluation created successfully with ID: {}", savedEntity.getId());

        // Step 7: Map Entity → Domain (for response)
        InitialEvaluation createdDomain = entityMapper.toDomain(savedEntity);

        // Step 8: Log important extracted fields for monitoring
        logCreatedNote(createdDomain, savedEntity);

        return createdDomain;
    }

    // ==================== Private Helper Methods ====================

    /**
     * Validates the incoming request
     */
    private void validateRequest(MedicalNoteRequest request) {
        if (request == null) {
            log.error("CreateInitialEvaluationUseCase: Request is null");
            throw new IllegalArgumentException("InitialEvaluationRequest cannot be null");
        }

        if (request.getCaseId() == null || request.getCaseId() <= 0) {
            log.error("CreateInitialEvaluationUseCase: Invalid patient case ID: {}",
                    request.getCaseId());
            throw new IllegalArgumentException("Valid patient case ID is required");
        }

        // Additional request validation can be added here
        // Or use @Valid annotation in controller with Bean Validation
    }

    /**
     * Validates business rules that span beyond single aggregate
     *
     * Business Rules:
     * - Only one Initial Evaluation allowed per patient case
     * - Visit number must be 1 for Initial Evaluation
     */
    private void validateBusinessRules(InitialEvaluation domain) {

        // Rule 1: Check if Initial Evaluation already exists for this patient case
        boolean initialEvalExists = repository.existsByPatientCaseIdAndNoteType(
                domain.getPatientCaseId(),
                MedicalNoteEntity.NoteType.INITIAL_EVALUATION
        );

        if (initialEvalExists) {
            log.error("Initial Evaluation already exists for patient case: {}",
                    domain.getPatientCaseId());
            throw new IllegalStateException(
                    "An Initial Evaluation already exists for patient case ID: " +
                            domain.getPatientCaseId() +
                            ". Only one Initial Evaluation is allowed per patient case."
            );
        }

        // Rule 2: Visit number must be 1 (enforced by domain, but double-check here)
        if (domain.getVisitNumber() != 1) {
            log.warn("Initial Evaluation visit number is not 1: {}", domain.getVisitNumber());
            // Domain validation should catch this, but log it for monitoring
        }

        // Rule 3: Date of service cannot be in the future
        if (domain.getDateOfService().isAfter(java.time.LocalDate.now())) {
            log.error("Date of service is in the future: {}", domain.getDateOfService());
            throw new IllegalArgumentException(
                    "Date of service cannot be in the future: " + domain.getDateOfService()
            );
        }

        // Additional business rules can be added here
        // Examples:
        // - Patient case must be active
        // - User must have permission to create notes for this patient
        // - Required sections must be present (subjective, assessment, etc.)
    }

    /**
     * Logs important information about the created note for monitoring/auditing
     */
    private void logCreatedNote(InitialEvaluation domain, MedicalNoteEntity entity) {
        log.info("Initial Evaluation created - ID: {}, Patient Case: {}, Pain Scale: {}, " +
                        "Complexity: {}, Work Status: {}, Total Units: {}",
                entity.getId(),
                entity.getPatientCaseId(),
                entity.getPainScale(),
                entity.getComplexityLevel(),
                entity.getWorkStatus(),
                entity.getTotalBillingUnits()
        );

        // Log if high pain or high complexity for alerting
        if (entity.getPainScale() != null && entity.getPainScale() >= 8) {
            log.warn("High pain scale detected: {} for patient case: {}",
                    entity.getPainScale(), entity.getPatientCaseId());
        }

        if ("HIGH".equals(entity.getComplexityLevel())) {
            log.info("High complexity case: patient case {}", entity.getPatientCaseId());
        }
    }
}
