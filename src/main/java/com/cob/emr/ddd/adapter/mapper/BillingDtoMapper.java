package com.cob.emr.ddd.adapter.mapper;

import com.cob.emr.model.medical.note.types.initial.evaluation.billing.Billing;
import com.cob.emr.ddd.domain.medicalnote.model.billing.BillingInformation;
import org.springframework.stereotype.Component;

/**
 * Maps Billing DTOs to BillingInformation domain model
 * TODO: Implement based on domain model structure
 */
@Component
public class BillingDtoMapper {

    public BillingInformation toDomain(Billing billingDto) {
        if (billingDto == null) {
            return null;
        }

        // TODO: Implement mapping from Angular billing DTO to domain
        // This will map:
        // - timedCodes
        // - untimedCodes
        // - supplies
        // - casts
        // - braces
        // - splintsOrthotics
        // - respiratory
        // - nerveConduction
        // - dailyNoteIncluded
        // - preTreatment
        // - postTreatment
        // - objectiveFindings
        // - instructions
        // - otherTreatmentProcedures

        return BillingInformation.builder()
                .build();
    }
}
