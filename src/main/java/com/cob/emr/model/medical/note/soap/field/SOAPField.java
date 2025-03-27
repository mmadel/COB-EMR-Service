package com.cob.emr.model.medical.note.soap.field;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SOAPField {
    private SOAPIDField idField;
    private String name;
    private String display;
    private String type;
    private Object value;
    private String selectValue;
    private String changeValueSelect;
    private Integer checkBoxCol;
    private String labelStyle;
    private String checkBoxStyle;
    private String parentField;
    private OMTFieldRequest omtFieldRequest;

}
