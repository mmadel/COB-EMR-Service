package com.cob.emr.model.patient.record;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientRecordRequest {
    private Long patientId;
    private String caseId;
    private Long clinicId;
    private String loggedIn;
}
