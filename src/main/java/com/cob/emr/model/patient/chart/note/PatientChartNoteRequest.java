package com.cob.emr.model.patient.chart.note;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class PatientChartNoteRequest {
    private Long patientId;
    private Long patientCaseId;
    private String noteId;
    private String noteStatus;
    private String noteType;
}
