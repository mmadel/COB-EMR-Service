package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SexualHistory {
    private final boolean sexuallyActive;
    private final String concerns;
}
