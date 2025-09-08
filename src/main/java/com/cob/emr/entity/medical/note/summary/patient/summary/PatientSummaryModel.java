package com.cob.emr.entity.medical.note.summary.patient.summary;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PatientSummaryModel {
    private String patientName;
    private String dob;
    private String insuranceName;
    private List<String> Diagnosis;
    private List<String> treatmentDiagnosis;
    private  String providerNumber;
}
