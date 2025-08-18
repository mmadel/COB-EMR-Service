package com.cob.emr.model.medical.note.types.initial.evaluation.plan;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Specialties {

    public Object orthoticFabricationSpecialtiesPlan;
    @JsonProperty("31_orthoticFabricationText")
    public Object _31_orthoticFabricationText;

    public Object tENSFittingSpecialtiesPlan;
    @JsonProperty("33_orthoticFabricationText")
    public Object _33_orthoticFabricationText;

    public Object acupunctureSpecialtiesPlan;
    @JsonProperty("37_acupunctureText")
    public Object _37_acupunctureText;

    public Object otherSpecialtiesPlan;
    @JsonProperty("39_otherText")
    public Object _39_otherText;
}
