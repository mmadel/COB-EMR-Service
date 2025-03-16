package com.cob.emr.model.medical.note.types.initial.evaluation.billing;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Billing {
    private List<Object> supplies;
    private String post_Treatment;
    private String pre_Treatment;
    private String objective_findings;
    private String instructionsTxt;
    private String precautions;
    private String dn_nstructions;
    private String dailyNoteIncluded;

    private UntimedCodes untimedCodes;
    private CalendarMonth calendarMonth;
    private NerveConduction nerveConduction;
    private Respiratory respiratory;
    private DirectTimedCodes directTimedCodes;
    private OtherTreatmentProcedures otherTreatmentProcedures;
    private Splintsorthotics splintsorthotics;
    private Casts casts;
    private Braces braces;
}
