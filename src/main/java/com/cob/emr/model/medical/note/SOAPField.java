package com.cob.emr.model.medical.note;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SOAPField {
    private SOAPIDField idField;
    private String name;
    private String display;
    private String type;
    private List<String> value;
    private String valueType;
    private String parentField;

}
