package com.cob.emr.model.medical.note.types.initial.evaluation.billing;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Respiratory {
    @JsonProperty("94620")
    public Integer _94620;
    @JsonProperty("94664")
    public Integer _94664;
    @JsonProperty("94668")
    public Integer _94668;
    @JsonProperty("G0237")
    public Integer g0237;
    @JsonProperty("G0238")
    public Integer g0238;
}
