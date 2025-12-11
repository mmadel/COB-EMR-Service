package com.cob.emr.ddd.domain.medicalnote.model.planofcare;

import com.cob.emr.ddd.domain.medicalnote.model.valueobjects.SpecialtyServiceType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SpecialtyService {
    private final SpecialtyServiceType serviceType;
    private final String details;
}
