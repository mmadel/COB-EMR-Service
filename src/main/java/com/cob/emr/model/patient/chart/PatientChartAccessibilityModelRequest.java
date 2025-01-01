package com.cob.emr.model.patient.chart;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PatientChartAccessibilityModelRequest {
    List<Long> allowedClinics;
    Long clinicId;
    Long patientId;
}
