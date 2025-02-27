package com.cob.emr.model.medical.note;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SOAPField {
    private String name;
    private String display;
    private String type;
    private String value;
    private List<SOAPField> dependents;

}
