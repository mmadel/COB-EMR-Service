package com.cob.emr.model.medical.note.finalize.init.exam.plan;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class InitAssessment {
    private List<String> shortTermGoals;
    private List<String> longTermGoals;
    private List<String> problems;
    private String assessmentDiagnosis;
    private String patientEducation;
    private String rehabPotential;
}
