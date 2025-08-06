package com.cob.emr.model.medical.note.types.initial.evaluation.billing;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OtherTreatmentProcedures {
    @JsonProperty("92542")
    public Integer _92542;

    @JsonProperty("92541")
    public Integer _92541;

    @JsonProperty("97810")
    public Integer _97810;

    @JsonProperty("97811")
    public Integer _97811;

    @JsonProperty("97813")
    public Integer _97813;

    @JsonProperty("97814")
    public Integer _97814;

    @JsonProperty("20999")
    public Integer _20999;
}
