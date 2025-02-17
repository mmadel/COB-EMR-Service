package com.cob.emr.model.patient.chart;

import com.cob.emr.enums.PatientChartAccessibility;
import com.cob.emr.model.clinic.ClinicModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@Builder
public class PatientChartAccessibilityModelResponse {
    PatientChartAccessibility patientChartAccessibility;
    List<ClinicModel> promptedClinics;
}
