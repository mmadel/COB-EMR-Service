package com.cob.emr.model.medical.note.types.initial.evaluation.subjective;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PriorFunction {
    @JsonProperty("0_selfCare")
    private Object selfCare;

    @JsonProperty("1_hygiene")
    private Object hygiene;

    @JsonProperty("2_grooming")
    private Object grooming;

    @JsonProperty("3_washingBodyParts")
    private Object washingBodyParts;

    @JsonProperty("4_washingWholeBody")
    private Object washingWholeBody;

    @JsonProperty("5_dryingOneself")
    private Object dryingOneself;

    @JsonProperty("6_caringForSkin")
    private Object caringForSkin;

    @JsonProperty("7_caringForTeeth")
    private Object caringForTeeth;

    @JsonProperty("8_caringForHair")
    private Object caringForHair;

    @JsonProperty("9_caringForNailsToeAndFinger")
    private Object caringForNailsToeAndFinger;

    @JsonProperty("10_lookingAfterOnesHealth")
    private Object lookingAfterOnesHealth;

    @JsonProperty("11_maintainingBalancedDiet")
    private Object maintainingBalancedDiet;

    @JsonProperty("12_keepingWarm")
    private Object keepingWarm;

    @JsonProperty("13_keepingCool")
    private Object keepingCool;

    @JsonProperty("14_gettingImmunizations")
    private Object gettingImmunizations;

    @JsonProperty("15_gettingRegularPhysicalExaminations")
    private Object gettingRegularPhysicalExaminations;

    @JsonProperty("16_dressing")
    private Object dressing;

    @JsonProperty("17_puttingOnClothes")
    private Object puttingOnClothes;

    @JsonProperty("18_puttingOnFootwear")
    private Object puttingOnFootwear;

    @JsonProperty("19_puttingOnAppropriateClothing")
    private Object puttingOnAppropriateClothing;

    @JsonProperty("20_removingClothes")
    private Object removingClothes;

    @JsonProperty("21_bathing")
    private Object bathing;

    @JsonProperty("22_showering")
    private Object showering;

    @JsonProperty("23_washingBodyParts")
    private Object washingBodyPartsDuplicate;

    @JsonProperty("24_dryingOneself")
    private Object dryingOneselfDuplicate;

    @JsonProperty("25_toileting")
    private Object toileting;

    @JsonProperty("26_regulatingUrination")
    private Object regulatingUrination;

    @JsonProperty("27_regulatingDefecation")
    private Object regulatingDefecation;

    @JsonProperty("28_regulatingMenstrualCare")
    private Object regulatingMenstrualCare;

    @JsonProperty("29_sleep")
    private Object sleep;

    @JsonProperty("30_disturbedSleep")
    private Object disturbedSleep;

    @JsonProperty("31_sleepingPostures")
    private Object sleepingPostures;

    @JsonProperty("32_prone")
    private Object prone;

    @JsonProperty("33_supine")
    private Object supine;

    @JsonProperty("34_sideR")
    private Object sideR;

    @JsonProperty("35_sideL")
    private Object sideL;

    @JsonProperty("36_pillows")
    private Object pillows;

    @JsonProperty("37_surface")
    private Object surface;

    @JsonProperty("38_firm")
    private Object firm;

    @JsonProperty("39_soft")
    private Object soft;

    @JsonProperty("40_sag")
    private Object sag;

    @JsonProperty("41_custom")
    private Object custom;

    @JsonProperty("42_iADLs")
    private Object iADLs;

    @JsonProperty("43_abilityToUseTelephone")
    private Object abilityToUseTelephone;

    @JsonProperty("44_shopping")
    private Object shopping;

    @JsonProperty("45_foodPreparation")
    private Object foodPreparation;

    @JsonProperty("46_housekeeping")
    private Object housekeeping;

    @JsonProperty("47_laundry")
    private Object laundry;

    @JsonProperty("48_modeOfTransportation")
    private Object modeOfTransportation;

    @JsonProperty("49_responsibilityForOwnMedications")
    private Object responsibilityForOwnMedications;

    @JsonProperty("50_abilityToHandleFinances")
    private Object abilityToHandleFinances;

    @JsonProperty("51_householdChores")
    private Object householdChores;

    @JsonProperty("52_cookAMeal")
    private Object cookAMeal;

    @JsonProperty("53_laundry")
    private Object laundryDuplicate;

    @JsonProperty("54_driveCommunityDistance")
    private Object driveCommunityDistance;

    @JsonProperty("55_volunteering")
    private Object volunteering;

    @JsonProperty("56_caregiving")
    private Object caregiving;

    @JsonProperty("91_communityIntegrationAccess")
    private Object communityIntegrationAccess;

    @JsonProperty("92_workVocationOccupation")
    private Object workVocationOccupation;

    @JsonProperty("93_recreation")
    private Object recreation;

    @JsonProperty("94_sports")
    private Object sports;

    @JsonProperty("65_other")
    private Object other;
}
