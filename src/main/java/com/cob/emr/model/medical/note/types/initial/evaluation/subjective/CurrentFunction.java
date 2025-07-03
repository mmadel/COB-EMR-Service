package com.cob.emr.model.medical.note.types.initial.evaluation.subjective;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CurrentFunction {
    @JsonProperty("95_selfCare")
    private Object selfCare;

    @JsonProperty("96_hygiene")
    private Object hygiene;

    @JsonProperty("97_grooming")
    private Object grooming;

    @JsonProperty("98_washingBodyParts")
    private Object washingBodyParts;

    @JsonProperty("99_washingWholeBody")
    private Object washingWholeBody;

    @JsonProperty("100_dryingOneself")
    private Object dryingOneself;

    @JsonProperty("101_caringForSkin")
    private Object caringForSkin;

    @JsonProperty("102_caringForTeeth")
    private Object caringForTeeth;

    @JsonProperty("103_caringForHair")
    private Object caringForHair;

    @JsonProperty("104_caringForNailsToeAndFinger")
    private Object caringForNailsToeAndFinger;

    @JsonProperty("105_lookingAfterOnesHealth")
    private Object lookingAfterOnesHealth;

    @JsonProperty("106_maintainingBalancedDiet")
    private Object maintainingBalancedDiet;

    @JsonProperty("107_keepingWarm")
    private Object keepingWarm;

    @JsonProperty("108_keepingCool")
    private Object keepingCool;

    @JsonProperty("109_gettingImmunizations")
    private Object gettingImmunizations;

    @JsonProperty("110_gettingRegularPhysicalExaminations")
    private Object gettingRegularPhysicalExaminations;

    @JsonProperty("111_dressing")
    private Object dressing;

    @JsonProperty("112_puttingOnClothes")
    private Object puttingOnClothes;

    @JsonProperty("113_puttingOnFootwear")
    private Object puttingOnFootwear;

    @JsonProperty("114_puttingOnAppropriateClothing")
    private Object puttingOnAppropriateClothing;

    @JsonProperty("115_removingClothes")
    private Object removingClothes;

    @JsonProperty("116_removingFootwear")
    private Object removingFootwear;

    @JsonProperty("117_bathing")
    private Object bathing;

    @JsonProperty("118_showering")
    private Object showering;

    @JsonProperty("119_washingBodyParts")
    private Object washingBodyPartsAgain;

    @JsonProperty("120_dryingOneself")
    private Object dryingOneselfAgain;

    @JsonProperty("121_toileting")
    private Object toileting;

    @JsonProperty("122_regulatingUrination")
    private Object regulatingUrination;

    @JsonProperty("123_regulatingDefecation")
    private Object regulatingDefecation;

    @JsonProperty("124_regulatingMenstrualCare")
    private Object regulatingMenstrualCare;

    @JsonProperty("125_sleep")
    private Object sleep;

    @JsonProperty("126_disturbedSleep")
    private Object disturbedSleep;

    @JsonProperty("127_sleepingPostures")
    private Object sleepingPostures;

    @JsonProperty("128_prone")
    private Object prone;

    @JsonProperty("129_supine")
    private Object supine;

    @JsonProperty("130_sideR")
    private Object sideR;

    @JsonProperty("131_sideL")
    private Object sideL;

    @JsonProperty("132_pillows")
    private Object pillows;

    @JsonProperty("133_surface")
    private Object surface;

    @JsonProperty("134_firm")
    private Object firm;

    @JsonProperty("135_soft")
    private Object soft;

    @JsonProperty("136_sag")
    private Object sag;

    @JsonProperty("137_custom")
    private Object custom;

    @JsonProperty("244_communityIntegrationAccess")
    private Object communityIntegrationAccess;

    @JsonProperty("245_workVocationOccupation")
    private Object workVocationOccupation;

    @JsonProperty("246_recreation")
    private Object recreation;

    @JsonProperty("247_sports")
    private Object sports;
}
