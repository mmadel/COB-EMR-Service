package com.cob.emr.model.medical.note.types.initial.evaluation.subjective;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
public class Pain {
    public String pain_scale;
    public List<Object> evals;
    public List<String> aggravatingFactors;
    public String restrictionsAndPainAlleviators;
    public String worseWith;
    public List<String> worseWithSelect;
    public String worseWithText;
    public String betterWith;
    public List<String> betterwithSelect;
    public String betterWithText;
    public String restrictionsAndPainAlleviatorsTextArea;
}
