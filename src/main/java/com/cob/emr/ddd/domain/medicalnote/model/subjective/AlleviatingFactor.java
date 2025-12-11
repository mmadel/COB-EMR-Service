package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AlleviatingFactor {
    private final String intervention;
    private final String description;
}
