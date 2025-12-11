package com.cob.emr.ddd.adapter.rest.medicalnote;

import com.cob.emr.ddd.adapter.dto.request.InitialEvaluationRequest;
import com.cob.emr.ddd.application.medicalnote.usecase.CreateInitialEvaluationUseCase;
import com.cob.emr.ddd.domain.medicalnote.model.InitialEvaluation;
import com.cob.emr.model.medical.note.MedicalNoteRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * REST Controller for Initial Evaluation Medical Notes
 *
 * Responsibilities:
 * - HTTP request/response handling
 * - Input validation (basic)
 * - Delegates business logic to Use Cases
 * - Error handling and HTTP status codes
 *
 * This is an ADAPTER in DDD - it adapts HTTP/REST to domain operations
 */
@RestController
@RequestMapping("/medical-notes/initial-evaluation")
public class InitialEvaluationController {

    private static final Logger log = LoggerFactory.getLogger(InitialEvaluationController.class);

    private final CreateInitialEvaluationUseCase createUseCase;

    public InitialEvaluationController(CreateInitialEvaluationUseCase createUseCase) {
        this.createUseCase = createUseCase;
    }

    /**
     * Create a new Initial Evaluation
     *
     * POST /api/medical-notes/initial-evaluation
     *
     * @param request Initial Evaluation data from Angular
     * @return Created Initial Evaluation with HTTP 201 Created
     */
    @PostMapping
    public ResponseEntity<?> createInitialEvaluation(
            @RequestBody MedicalNoteRequest request) {

        log.info("POST /api/medical-notes/initial-evaluation - Creating Initial Evaluation for patient case: {}",
                request != null ? request.getCaseId() : "null");

        try {
            // Execute use case
            InitialEvaluation created = createUseCase.execute(request);

            // Build success response
            Map<String, Object> response = buildSuccessResponse(created);

            log.info("Initial Evaluation created successfully with ID: {}",
                    extractIdFromDomain(created));

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);

        } catch (IllegalArgumentException e) {
            // Bad request - validation error
            log.warn("Validation error creating Initial Evaluation: {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(buildErrorResponse("Validation Error", e.getMessage()));

        } catch (IllegalStateException e) {
            // Business rule violation
            log.warn("Business rule violation creating Initial Evaluation: {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(buildErrorResponse("Business Rule Violation", e.getMessage()));

        } catch (Exception e) {
            // Unexpected error
            log.error("Unexpected error creating Initial Evaluation", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(buildErrorResponse("Internal Server Error",
                            "An unexpected error occurred. Please try again later."));
        }
    }

    /**
     * Health check endpoint
     *
     * GET /api/medical-notes/initial-evaluation/health
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "Initial Evaluation Controller");
        response.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.ok(response);
    }

    // ==================== Helper Methods ====================

    /**
     * Builds a success response with the created domain object
     *
     * In a real application, you might want to create a Response DTO
     * and map Domain → Response DTO to control what's exposed to the client
     */
    private Map<String, Object> buildSuccessResponse(InitialEvaluation domain) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Initial Evaluation created successfully");
        response.put("data", domain);
        response.put("timestamp", LocalDateTime.now());

        // Add quick access fields
        Map<String, Object> summary = new HashMap<>();
        summary.put("patientCaseId", domain.getPatientCaseId());
        summary.put("visitNumber", domain.getVisitNumber());
        summary.put("dateOfService", domain.getDateOfService());
        summary.put("status", domain.getStatus());
        summary.put("painScale", domain.getPainScale());
        summary.put("workStatus", domain.getWorkStatus());
        summary.put("complexityLevel", domain.getComplexityLevel());

        response.put("summary", summary);

        return response;
    }

    /**
     * Builds an error response
     */
    private Map<String, Object> buildErrorResponse(String error, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("error", error);
        response.put("message", message);
        response.put("timestamp", LocalDateTime.now());
        return response;
    }

    /**
     * Extracts ID from domain object (if available after persistence)
     * Note: Domain model doesn't have ID, but we can infer it was saved successfully
     */
    private String extractIdFromDomain(InitialEvaluation domain) {
        // In a complete implementation, you might return an ID from the use case
        // For now, we know it was created successfully
        return "Successfully created (ID available in database)";
    }
}
