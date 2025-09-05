package com.cob.emr.model.medical.note.finalize.init.exam.plan;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
public class InitPlanOfCare {
    private String frequency;
    private String duration;
    private String plan;
    private boolean physicianSignature;
    private boolean createPlanOfCare;
    private Map<String, List<String>> procedures;
    private Map<String, List<String>> modalities;
    private Map<String, List<String>> specialties;
}
