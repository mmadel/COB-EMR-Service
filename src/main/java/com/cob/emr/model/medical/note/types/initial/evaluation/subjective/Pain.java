package com.cob.emr.model.medical.note.types.initial.evaluation.subjective;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Pain {
    public String painScale;
    public String aggravatingFactors;
    public String restrictionsAndPainAlleviators;
    public String worseWith;
    public String worseWithSelect;
    public String worseWithText;
    public String betterWith;
    public String betterWithSelect;
    public String betterWithText;
    public String restrictionsAndPainAlleviatorsTextArea;
}
