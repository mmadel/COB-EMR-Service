package com.cob.emr.model.medical.note.omt.test;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OMTTestValues {
    private Long id;
    private Long medicalNoteId;
    private Object values;
    private String testName;
}
