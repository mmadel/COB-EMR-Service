package com.cob.emr.model.medical.note.mapper;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FieldDefinition {
    private String id;       // UUID
    private String name;     // field_name
    private String display;  // field_display
    private String parentId; // parent UUID
    private List<FieldDefinition> children = new ArrayList<>();
}