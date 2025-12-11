package com.cob.emr.ddd.domain.medicalnote.model.planofcare;

import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.ProcedureCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TherapeuticProcedure {
    private final ProcedureCategory procedureCategory;
    private final String procedureType;
    private final String details;
}
