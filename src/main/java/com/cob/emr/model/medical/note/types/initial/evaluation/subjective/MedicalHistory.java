package com.cob.emr.model.medical.note.types.initial.evaluation.subjective;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MedicalHistory {

    public Object previousHistoryOfSimilarSymptomsMedicalHistory;
    public Object previousEpisodesOfSameComplaintsPreviousHistoryOfSimilarSymptomsMedicalHistory;
    public Object yearOfFirstEpisodePreviousEpisodesOfSameComplaintsPreviousHistoryOfSimilarSymptomsMedicalHistory;
    public Object ageRangePreviousEpisodesOfSameComplaintsPreviousHistoryOfSimilarSymptomsMedicalHistory;

    public Object previousTreatmentsForSimilarSymptomsPreviousHistoryOfSimilarSymptomsMedicalHistory;
    public Object commentPreviousTreatmentsForSimilarSymptomsPreviousHistoryOfSimilarSymptomsMedicalHistory;


    @JsonProperty("252_previousEpisodesOfSameComplaints")
    public Object _252_previousEpisodesOfSameComplaints;
    @JsonProperty("253_ageRange")
    public String _253_ageRange;
    @JsonProperty("254_yearOfFirstEpisode")
    public String _254_yearOfFirstEpisode;
    @JsonProperty("255_previousTreatmentsForSimilarSymptoms")
    public Object _255_previousTreatmentsForSimilarSymptoms;
    @JsonProperty("256_comment")
    public Object _256_comment;

    public String generalHealthMedicalHistory;


    public Object occupationSocialHistoryMedicalHistory;
    public Object homeLayoutOccupationSocialHistoryMedicalHistory;
    public Object occupationAndWorkStatusOccupationSocialHistoryMedicalHistory;
    public Object durableMedicalEquipmentOccupationSocialHistoryMedicalHistory;
    public Object isPatientATobaccoUserOccupationSocialHistoryMedicalHistory;
    public Object socialHistoryOccupationSocialHistoryMedicalHistory;

    @JsonProperty("259_socialHistory")
    public Object _259_socialHistory;
    @JsonProperty("260_socialHistorySelect")
    public Object _260_socialHistorySelect;
    @JsonProperty("261_occupationAndWorkStatus")
    public Object _261_occupationAndWorkStatus;
    @JsonProperty("262_nameOfOccupation")
    public Object _262_nameOfOccupation;
    @JsonProperty("263_status")
    public String _263_status;
    @JsonProperty("264_dutyLevel")
    public String _264_dutyLevel;
    @JsonProperty("265_description")
    public Object _265_description;
    @JsonProperty("266_outOfWorkSince")
    public Object _266_outOfWorkSince;
    @JsonProperty("267_returnToWorkDate")
    public Object _267_returnToWorkDate;
    @JsonProperty("268_workersCompensation")
    public Object _268_workersCompensation;
    @JsonProperty("269_litigation")
    public Object _269_litigation;
    @JsonProperty("270_homeLayout")
    public Object _270_homeLayout;
    @JsonProperty("271_homeLayoutSelect")
    public Object _271_homeLayoutSelect;
    @JsonProperty("272_homeLayoutText")
    public Object _272_homeLayoutText;
    @JsonProperty("273_durableMedicalEquipment")
    public Object _273_durableMedicalEquipment;
    @JsonProperty("274_durableMedicalEquipmentSelect")
    public Object _274_durableMedicalEquipmentSelect;
    @JsonProperty("275_durableMedicalEquipmentText")
    public Object _275_durableMedicalEquipmentText;
    @JsonProperty("276_isPatientATobaccoUser")
    public Object _276_isPatientATobaccoUser;
    @JsonProperty("277_doesPatientSmokeCigarettesAndOrUseOtherFormsOfTobacco")
    public Object _277_doesPatientSmokeCigarettesAndOrUseOtherFormsOfTobacco;
    @JsonProperty("278_recommendationMadeToStopUsingTobaccoProducts")
    public Object _278_recommendationMadeToStopUsingTobaccoProducts;
    @JsonProperty("279_adviceAndSupportProvidedForTobaccoUseCessation")
    public Object _279_adviceAndSupportProvidedForTobaccoUseCessation;
    @JsonProperty("280_continuedSupportForTobaccoUseCessationWillBeProvidedOnAllOrMostFollowUpVisits")
    public Object _280_continuedSupportForTobaccoUseCessationWillBeProvidedOnAllOrMostFollowUpVisits;
    @JsonProperty("281_patientSmokeCigarettesText")
    public Object _281_patientSmokeCigarettesText;

    public Object homeHealthCareMedicalHistory;

    @JsonProperty("283_homeHealthCareText")
    public Object _283_homeHealthCareText;

    public Object historyOfFallsMedicalHistory;

    @JsonProperty("70_fallsDocumentedDoYouHaveDocumentationThatSupportsThePatientsFallHistory")
    public Object _70_fallsDocumentedDoYouHaveDocumentationThatSupportsThePatientsFallHistory;
    @JsonProperty("71_fallHistoryText")
    public Object _71_fallHistoryText;
    @JsonProperty("72_fallsDocumentedDoYouHaveDocumentationThatSupportsThePatientsFallHistory")
    public Object _72_fallsDocumentedDoYouHaveDocumentationThatSupportsThePatientsFallHistory;
    @JsonProperty("73_fallHistoryText")
    public Object _73_fallHistoryText;
    @JsonProperty("74_riskAssessment")
    public Object _74_riskAssessment;

    public MedicalHistorySub medicalHistoryMedicalHistory;

    public Object diagnosticTestingImagingMedicalHistory;


    public ComplicatingPersonalFactors complicatingPersonalFactorsMedicalHistory;



    public Object medicalHistoryReviewMedicalHistory;

    public boolean medical_history_review_assessment_low_complexity;
    public boolean medical_history_review_assessment_moderate_complexity;
    public boolean medical_history_review_assessment_high_complexity;
    public boolean medical_history_review_other;

    public Object mentalStatusCognitiveFunctionAppearsImpairedMedicalHistory;

    @JsonProperty("5_mentalstatuscognitivefunctionappearsimpairedtxt")
    public Object _5_mentalstatuscognitivefunctionappearsimpairedtxt;

    public Object unexplainedWeightLossMedicalHistory;


    public CurrentMedications currentMedicationsMedicalHistory;



    public Object patientGoalsMedicalHistory;
}
