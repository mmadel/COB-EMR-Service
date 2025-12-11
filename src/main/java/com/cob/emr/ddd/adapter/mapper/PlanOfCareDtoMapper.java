package com.cob.emr.ddd.adapter.mapper;

import com.cob.emr.model.medical.note.types.initial.evaluation.plan.PlanOfCare;
import org.springframework.stereotype.Component;

/**
 * Maps PlanOfCare DTOs to PlanOfCare domain model
 * TODO: Implement based on domain model structure
 */
@Component
public class PlanOfCareDtoMapper {

    public com.cob.emr.ddd.domain.medicalnote.model.planofcare.PlanOfCare toDomain(PlanOfCare planDto) {
        if (planDto == null) {
            return null;
        }

        // TODO: Implement mapping from Angular planOfCare DTO to domain
        // This will map:
        // - treatmentPlan
        // - modalities
        // - procedures
        // - specialties
        // - instructions
        // - requiresPhysicianSignature
        // - createPlanOfCare

        return com.cob.emr.ddd.domain.medicalnote.model.planofcare.PlanOfCare.builder()
                .build();
    }
}
