package com.cob.emr.model.patient.chart.note;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class PatientChartNoteRequest {
    private String patientCaseUuid;
    private String noteId;
    private String noteStatus;
    private String noteType;
    private LocalDateTime encounterDate;
    private LocalDate dos;

}
