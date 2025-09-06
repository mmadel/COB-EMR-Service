package com.cob.emr.service.mapper.dto;

import java.util.List;

public class FieldWrapper {
    private List<FieldEntry> fields;

    public FieldWrapper(List<FieldEntry> fields) {
        this.fields = fields;
    }

    public List<FieldEntry> getFields() { return fields; }
}