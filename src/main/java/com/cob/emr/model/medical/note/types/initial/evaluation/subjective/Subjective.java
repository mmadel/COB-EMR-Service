package com.cob.emr.model.medical.note.types.initial.evaluation.subjective;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Subjective {
    private Basic basic;
    private Pain pain;
    private PriorFunction priorFunction;
    private CurrentFunction currentFunction;
    private MedicalHistory medicalHistory;
}
