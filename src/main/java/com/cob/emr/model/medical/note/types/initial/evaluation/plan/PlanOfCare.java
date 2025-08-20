package com.cob.emr.model.medical.note.types.initial.evaluation.plan;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PlanOfCare {
    private boolean createPlanOfCare;
    private String frequency;
    private String duration;
    private String plan;
    private boolean physicianSignature;
    private Procedures procedures;
    private Modalities modalities;
    private Specialties specialties;

    private Object instructions;
    private Object instructionTxt;
}
