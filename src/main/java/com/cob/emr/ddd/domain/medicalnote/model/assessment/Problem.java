package com.cob.emr.ddd.domain.medicalnote.model.assessment;

import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.ProblemStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Problem {
    private final String description;
    private final ProblemStatus status;
    
    public boolean isActive() {
        return status == ProblemStatus.ACTIVE;
    }
}
