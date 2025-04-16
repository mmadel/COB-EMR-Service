package com.cob.emr.model.patient.record;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PatientRecord {
    private Long entityId;
    private String status;
    private Long date;
    private boolean isNote;
    private List<String> actions;
}
