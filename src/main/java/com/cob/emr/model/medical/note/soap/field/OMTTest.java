package com.cob.emr.model.medical.note.soap.field;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OMTTest {
    private List<String> name;
    private String id;
    private String style;
    private String type;
    private Object value;
    private String selectValue;
}
