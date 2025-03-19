package com.cob.emr.model.patient.record;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientRecord {
    private Long entityId;
    private String status;
    private Long date;
}
