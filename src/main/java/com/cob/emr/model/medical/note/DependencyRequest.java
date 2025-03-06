package com.cob.emr.model.medical.note;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class DependencyRequest {
    public List<String[]> dependencies;
}
