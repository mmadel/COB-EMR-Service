package com.cob.emr.repositories.audit;

import com.cob.emr.entity.audit.AuditLog;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class AuditLogSpecification {

    public static Specification<AuditLog> withFilters(Long clinicId,
                                                      String performedByUuid,
                                                      Long patientId,
                                                      Long startDateMillis,
                                                      Long endDateMillis) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("clinicId"), clinicId));

            if (performedByUuid != null) {
                predicates.add(cb.equal(root.get("performedBy"), performedByUuid));
            }
            if (patientId != null) {
                predicates.add(cb.equal(root.get("patientId"), patientId));
            }
            if (startDateMillis != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("performedAt"), startDateMillis));
            }
            if (endDateMillis != null) {
                long effectiveEnd = endDateMillis;
                if (startDateMillis != null && startDateMillis.equals(endDateMillis)) {
                    effectiveEnd = endDateMillis + 86_400_000L;
                }
                predicates.add(cb.lessThan(root.get("performedAt"), effectiveEnd));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
