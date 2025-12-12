package com.cob.emr.entity.patient.chart.note;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "patient_chart_note")
@Getter
@Setter
public class PatientChartNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "patient_id")
    private Long patientId;

    @Column(name = "patient_case_id")
    private Long patientCaseId;

    @Column(name = "note_id")
    private String noteId;

    @Column(name = "note_status")
    private String noteStatus;

    @Column(name = "note_type")
    private String noteType;
}
