package com.cob.emr.ddd.domain.medicalnote.model.subjective;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PregnancyHistory {
    private final int numberOfPregnancies;
    private final int numberOfLiveBirths;
    private final String complications;
}
