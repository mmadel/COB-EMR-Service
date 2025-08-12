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

    public Object assessmentDiagnosisAssessment;


    public Object patientClinicalPresentationAssessment;

    public boolean patient_clinical_presentation_low_complexity;
    public boolean patient_clinical_presentation_moderate_complexity;
    public boolean patient_clinical_presentation_high_complexity;
    public boolean medical_history_review_other;


    public Object parentPatientEducationAssessment;

    public String rehabPotentialAssessment;


    public Object rehabPotentialTxtareaRehabPotentialAssessment;


    public Object reasonAssessment;


    public Object contraindicationsToTherapyAssessment;


    public Object contraindicationsToTherapyTextContraindicationsToTherapyAssessment;


    public Object agreesToTheCurrentTreatmentPlanConcentAssessment;


    public Object consentToCareAssessment;
}
