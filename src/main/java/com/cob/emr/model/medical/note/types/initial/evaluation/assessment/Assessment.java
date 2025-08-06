package com.cob.emr.model.medical.note.types.initial.evaluation.assessment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
public class Assessment {
    public ArrayList<Object> problems;
    public ArrayList<Object> goals;
    @JsonProperty("9_assessmentDiagnosis")
    public Object _9_assessmentDiagnosis;
    @JsonProperty("10_patientClinicalPresentation")
    public Object _10_patientClinicalPresentation;
    public boolean patient_clinical_presentation_low_complexity;
    public boolean patient_clinical_presentation_moderate_complexity;
    public boolean patient_clinical_presentation_high_complexity;
    public boolean medical_history_review_other;
    @JsonProperty("11_parentPatientEducation")
    public Object _11_parentPatientEducation;
    @JsonProperty("12_rehabPotential")
    public String _12_rehabPotential;
    @JsonProperty("13_rehabPotentialtxtArea")
    public Object _13_rehabPotentialtxtArea;
    @JsonProperty("14_reason")
    public Object _14_reason;
    @JsonProperty("15_contraindicationsToTherapy")
    public Object _15_contraindicationsToTherapy;
    @JsonProperty("16_contraindicationsToTherapyText")
    public Object _16_contraindicationsToTherapyText;
    @JsonProperty("17_agreesToTheCurrentTreatmentPlanConcent")
    public Object _17_agreesToTheCurrentTreatmentPlanConcent;
    @JsonProperty("18_consentToCare")
    public Object _18_consentToCare;
}
