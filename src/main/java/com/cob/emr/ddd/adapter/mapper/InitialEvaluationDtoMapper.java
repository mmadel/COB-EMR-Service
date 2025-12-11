package com.cob.emr.ddd.adapter.mapper;


import com.cob.emr.ddd.domain.medicalnote.model.InitialEvaluation;
import com.cob.emr.ddd.domain.medicalnote.model.assessment.ClinicalAssessment;
import com.cob.emr.ddd.domain.medicalnote.model.billing.BillingInformation;
import com.cob.emr.ddd.domain.medicalnote.model.objective.ObjectiveFindings;
import com.cob.emr.ddd.domain.medicalnote.model.planofcare.PlanOfCare;
import com.cob.emr.ddd.domain.medicalnote.model.subjective.SubjectiveFindings;
import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.NoteStatus;
import com.cob.emr.model.medical.note.MedicalNoteRequest;
import com.cob.emr.model.medical.note.types.initial.evaluation.InitialExamination;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Main mapper for InitialEvaluation
 * Orchestrates mapping from Angular DTOs to Domain Model
 */
@Component
public class InitialEvaluationDtoMapper {

    private final SubjectiveDtoMapper subjectiveMapper;
    private final ObjectiveDtoMapper objectiveMapper;
    private final AssessmentDtoMapper assessmentMapper;
    private final PlanOfCareDtoMapper planOfCareMapper;
    private final BillingDtoMapper billingMapper;

    public InitialEvaluationDtoMapper(
            SubjectiveDtoMapper subjectiveMapper,
            ObjectiveDtoMapper objectiveMapper,
            AssessmentDtoMapper assessmentMapper,
            PlanOfCareDtoMapper planOfCareMapper,
            BillingDtoMapper billingMapper) {
        this.subjectiveMapper = subjectiveMapper;
        this.objectiveMapper = objectiveMapper;
        this.assessmentMapper = assessmentMapper;
        this.planOfCareMapper = planOfCareMapper;
        this.billingMapper = billingMapper;
    }

    /**
     * Maps InitialEvaluationRequest (Angular DTO) to InitialEvaluation domain model
     */
    public InitialEvaluation toDomain(MedicalNoteRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("InitialEvaluationRequest cannot be null");
        }

        // Map each section
        SubjectiveFindings subjective = subjectiveMapper.toDomain(request.getSubjective());
        ObjectiveFindings objective = objectiveMapper.toDomain(request.getObjective());
        ClinicalAssessment assessment = assessmentMapper.toDomain(request.getAssessment());
        PlanOfCare planOfCare = planOfCareMapper.toDomain(request.getPlanOfCare());
        BillingInformation billing = billingMapper.toDomain(request.getBilling());

        // Parse top-level fields
        Long patientCaseId = request.getCaseId() != null ? request.getCaseId() : 0L;
        int visitNumber = request.getVisitNumber() != null ? request.getVisitNumber() : 1;
        LocalDate dateOfService = parseDate(request.getDateOfService() +"");
        NoteStatus status = parseNoteStatus(request.getStatus());
        String createdBy = request.getCreatedBy() != null ? request.getCreatedBy() : "SYSTEM";
        LocalDateTime createdAt = parseDateTime(request.getCreatedAt());

        // Create domain object using constructor
        return new InitialEvaluation(
                patientCaseId,
                visitNumber,
                dateOfService,
                status,
                createdBy,
                createdAt,
                subjective,
                objective,
                assessment,
                planOfCare,
                billing
        );
    }

    // ==================== Helper Methods ====================

    private LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return LocalDate.now();
        }
        try {
            return LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (Exception e) {
            return LocalDate.now();
        }
    }

    private LocalDateTime parseDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.trim().isEmpty()) {
            return LocalDateTime.now();
        }
        try {
            // Try ISO format first
            return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (Exception e) {
            try {
                // Try with space separator: "2025-11-04 11:10:22"
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                return LocalDateTime.parse(dateTimeStr, formatter);
            } catch (Exception ex) {
                return LocalDateTime.now();
            }
        }
    }

    private NoteStatus parseNoteStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return NoteStatus.DRAFT;
        }
        try {
            return NoteStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            return NoteStatus.DRAFT;
        }
    }
}
