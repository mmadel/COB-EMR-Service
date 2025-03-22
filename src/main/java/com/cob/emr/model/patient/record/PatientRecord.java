package com.cob.emr.model.patient.record;

import com.cob.emr.enums.PatientRecordLineAction;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PatientRecord {
    private Long entityId;
    private String status;
    private Long date;
    private List<String> actions;
}
