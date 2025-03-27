package com.cob.emr.model.medical.note.soap.field;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class FieldRequest {
    private String fieldName;
    private String fieldType;
    private String selectValue;
    private Object value;
    private Object changeValue;
    private boolean ignoreDisplay;
    private String changeValueSelect;
    private List<FieldRequest> dependencies;
    private OMTFieldRequest omtFieldRequest;
}
