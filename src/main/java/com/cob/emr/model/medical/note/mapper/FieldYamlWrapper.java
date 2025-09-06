package com.cob.emr.model.medical.note.mapper;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FieldYamlWrapper {
    private List<FieldDefinition> fields;
}