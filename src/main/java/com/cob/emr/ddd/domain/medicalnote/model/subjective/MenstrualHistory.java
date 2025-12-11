package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenstrualHistory {
    private final boolean isRegular;
    private final String details;
}
