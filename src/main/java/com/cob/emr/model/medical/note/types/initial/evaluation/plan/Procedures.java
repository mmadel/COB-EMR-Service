package com.cob.emr.model.medical.note.types.initial.evaluation.plan;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Procedures {
    public Object therapeuticExercisesPlan;

    public Object romTherapeuticExercisesPlan;

    public Object strengthTherapeuticExercisesPlan;

    public Object enduranceTherapeuticExercisesPlan;

    public Object stabilityTherapeuticExercisesPlan;


    public Object therapeuticExercisesTherapeuticExercisesPlan;


    public Object therapeuticActivityPlan;


    public Object workSpecificTherapeuticActivityPlan;


    public Object sportSpecificTherapeuticActivityPlan;

    public Object transfersTherapeuticActivityPlan;

    public Object bedMobilityTherapeuticActivityPlan;

    public Object adlSpecificTherapeuticActivityPlan;


    public Object therapeuticActivityTextAreaTherapeuticActivityPlan;


    public Object gaitTrainingPlan;

    @JsonProperty("4PointWalkerGaitTrainingPlan")
    public Object _4PointWalkerGaitTrainingPlan;

    public Object frontWheelWalkerGaitTrainingPlan;

    @JsonProperty("4WheelWalkerGaitTrainingPlan")
    public Object _4WheelWalkerGaitTrainingPlan;

    public Object hemiWalkerGaitTrainingPlan;

    public Object quadCaneGaitTrainingPlan;

    @JsonProperty("1PointCaneGaitTrainingPlan")
    public Object _1PointCaneGaitTrainingPlan;

    @JsonProperty("2AxillaryCrutchesGaitTrainingPlan")
    public Object _2AxillaryCrutchesGaitTrainingPlan;

    @JsonProperty("1AxillaryCrutchGaitTrainingPlan")
    public Object _1AxillaryCrutchGaitTrainingPlan;

    @JsonProperty("2ForearmCrutchesGaitTrainingPlan")
    public Object _2ForearmCrutchesGaitTrainingPlan;

    @JsonProperty("1ForearmCrutchesGaitTrainingPlan")
    public Object _1ForearmCrutchesGaitTrainingPlan;


    public Object evenSurfacesGaitTrainingPlan;

    public Object unevenSurfacesGaitTrainingPlan;


    public Object stairsGaitTrainingPlan;

    public Object curbsGaitTrainingPlan;


    public Object gaitTrainingTextAreaGaitTrainingPlan;

    public Object neuromuscularRehabilitationPlan;


    public Object balanceProprioceptionTrainingNeuromuscularRehabilitationPlan;


    public Object muscleReEducationNeuromuscularRehabilitationPlan;

    public Object sequencingNeuromuscularRehabilitationPlan;

    public Object coordinationNeuromuscularRehabilitationPlan;

    public Object pnfNeuromuscularRehabilitationPlan;


    public Object redcordNeuracNeuromuscularActivationNeuromuscularRehabilitationPlan;

    public Object neuromuscularTextNeuromuscularRehabilitationPlan;


    public Object manualTherapyPlan;

    @JsonProperty("25_softTissueMobilization")
    public Object _25_softTissueMobilization;
    @JsonProperty("26_jointMobilization")
    public Object _26_jointMobilization;
    @JsonProperty("27_spinalMobilization")
    public Object _27_spinalMobilization;
    @JsonProperty("28_manualTraction")
    public Object _28_manualTraction;
    @JsonProperty("29_myofascialRelease")
    public Object _29_myofascialRelease;
    @JsonProperty("30_muscleEnergyTechniques")
    public Object _30_muscleEnergyTechniques;
    @JsonProperty("31_manualResistiveExercise")
    public Object _31_manualResistiveExercise;
    @JsonProperty("32_patellarMobs")
    public Object _32_patellarMobs;
    @JsonProperty("33_cranioSacral")
    public Object _33_cranioSacral;
    @JsonProperty("34_visceralManipulation")
    public Object _34_visceralManipulation;
    @JsonProperty("35_dryNeedlingIntramuscularManualTherapy")
    public Object _35_dryNeedlingIntramuscularManualTherapy;
    @JsonProperty("36_grastonOrASTYMTechniques")
    public Object _36_grastonOrASTYMTechniques;
    @JsonProperty("37_strainCounterstrain")
    public Object _37_strainCounterstrain;
    @JsonProperty("38_manualTherapyText")
    public Object _38_manualTherapyText;


    public Object massagePlan;

    @JsonProperty("40_massageText")
    public Object _40_massageText;


    public Object aquaticTherapyPlan;

    @JsonProperty("42_aquaticTherapyText")
    public Object _42_aquaticTherapyText;

    public Object splintingTapingPlan;

    @JsonProperty("44_splintingTapingText")
    public Object _44_splintingTapingText;

    public Object canalithRepositioningPlan;

    @JsonProperty("46_canalithRepositioningText")
    public Object _46_canalithRepositioningText;

    public Object positionalNystagmusTestMinimum4PositionsWRecordingPlan;

    @JsonProperty("48_positionalNystagmusText")
    public Object _48_positionalNystagmusText;

    public Object spontaneousNystagmusTestWGazeFixationNystagmusWRecordingPlan;

    @JsonProperty("50_spontaneousNystagmusText")
    public Object _50_spontaneousNystagmusText;


    public Object woundCareDebridementPlan;

    @JsonProperty("52_woundCareDebridementText")
    public Object _52_woundCareDebridementText;

    public Object iontophoresisPlan;

    @JsonProperty("54_dexamethasone")
    public Object _54_dexamethasone;
    @JsonProperty("55_lidocaine")
    public Object _55_lidocaine;
    @JsonProperty("56_marcaine")
    public Object _56_marcaine;
    @JsonProperty("57_aceticAcid")
    public Object _57_aceticAcid;
    @JsonProperty("58_iodine")
    public Object _58_iodine;
    @JsonProperty("59_iontophoresisText")
    public Object _59_iontophoresisText;

    public Object groupTherapyPlan;

    @JsonProperty("61_groupTherapyText")
    public Object _61_groupTherapyText;

    public Object lymphedemaPlan;

    @JsonProperty("63_lymphedemaText")
    public Object _63_lymphedemaText;


    public Object cardiacRehabilitationPlan;

    @JsonProperty("65_cardiacRehabilitationText")
    public Object _65_cardiacRehabilitationText;

    public Object vestibularRehabilitationPlan;

    @JsonProperty("67_vestibularRehabilitationText")
    public Object _67_vestibularRehabilitationText;

    public Object patientEducationPlan;

    @JsonProperty("69_homeExerciseProgram")
    public Object _69_homeExerciseProgram;
    @JsonProperty("70_posturalTraining")
    public Object _70_posturalTraining;
    @JsonProperty("71_ergonomics")
    public Object _71_ergonomics;
    @JsonProperty("72_liftingMechanics")
    public Object _72_liftingMechanics;
    @JsonProperty("73_tENSUse")
    public Object _73_tENSUse;
    @JsonProperty("74_activityModification")
    public Object _74_activityModification;
    @JsonProperty("75_homeSafety")
    public Object _75_homeSafety;
    @JsonProperty("76_patientEducationText")
    public Object _76_patientEducationText;


    public Object selfCarePlan;

    @JsonProperty("78_selfCareText")
    public Object _78_selfCareText;


    public Object cognitionPlan;

    @JsonProperty("80_cognitionText")
    public Object _80_cognitionText;


    public Object remoteTherapeuticMonitoringPlan;
}
