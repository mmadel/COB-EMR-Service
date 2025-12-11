package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AggravatingFactor {
    private final String activity;
    private final String description;
}
