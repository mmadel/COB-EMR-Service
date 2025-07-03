package com.cob.emr.model.medical.note.soap.field.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class DependencyRequest {
    public List<String[]> dependencies;
}
