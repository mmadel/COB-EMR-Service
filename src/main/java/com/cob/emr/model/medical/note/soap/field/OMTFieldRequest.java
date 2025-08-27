package com.cob.emr.model.medical.note.soap.field;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OMTFieldRequest {
    private List<OMTTest> list;
    private String layout;
    private String testName;
}
