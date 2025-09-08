package com.cob.emr.model.medical.note.summary.soap;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
public class PlanOfCareSummary {
    private String frequency;
    private String duration;
    private String plan;
    private boolean physicianSignature;
    private boolean createPlanOfCare;
    private Map<String, List<String>> procedures;
    private Map<String, List<String>> modalities;
    private Map<String, List<String>> specialties;
}
