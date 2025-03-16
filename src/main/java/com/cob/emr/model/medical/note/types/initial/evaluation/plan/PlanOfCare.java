package com.cob.emr.model.medical.note.types.initial.evaluation.plan;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PlanOfCare {
    private String createPlanOfCare;
    private String frequency;
    private String duration;
    private String plan;
    private String physicianSignature;
    private Procedures procedures;
    private Modalities modalities;
    private Specialties specialties;
}
