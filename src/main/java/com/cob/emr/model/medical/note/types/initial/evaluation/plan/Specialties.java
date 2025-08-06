package com.cob.emr.model.medical.note.types.initial.evaluation.plan;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Specialties {
    @JsonProperty("30_orthoticFabrication")
    public Object _30_orthoticFabrication;
    @JsonProperty("31_orthoticFabricationText")
    public Object _31_orthoticFabricationText;
    @JsonProperty("32_tENSFitting")
    public Object _32_tENSFitting;
    @JsonProperty("33_orthoticFabricationText")
    public Object _33_orthoticFabricationText;
    @JsonProperty("36_acupuncture")
    public Object _36_acupuncture;
    @JsonProperty("37_acupunctureText")
    public Object _37_acupunctureText;
    @JsonProperty("38_other")
    public Object _38_other;
    @JsonProperty("39_otherText")
    public Object _39_otherText;
}
