package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BladderFunction {
    private final String concerns;
    private final String pattern;
}
