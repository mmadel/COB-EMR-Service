package com.cob.emr.model.medical.note.types.initial.evaluation.billing;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Billing {
    private String post_Treatment;
    private String pre_Treatment;
    private String objective_findings;
    private String instructionsTxt;
    private String precautions;
    private String dn_nstructions;
    private boolean dailyNoteIncluded;
    private List<CPTModel> untimedCodes;
    private List<CPTModel> calendarMonth;
    private List<CPTModel> nerveConduction;
    private List<CPTModel> respiratory;
    private List<CPTModel> directTimedCodes;
    private List<CPTModel> supplies;
    private List<CPTModel> otherTreatmentProcedures;
    private List<CPTModel> splintsorthotics;
    private List<CPTModel> casts;
    private List<CPTModel> braces;
}
