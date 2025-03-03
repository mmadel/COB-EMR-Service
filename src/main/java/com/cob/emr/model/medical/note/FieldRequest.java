package com.cob.emr.model.medical.note;

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
    private String changeValue;
    private boolean ignoreDisplay;
    private List<FieldRequest> dependencies;
}
