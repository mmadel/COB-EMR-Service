package com.cob.emr.entity.patient.chart.note;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "patient_chart_note")
@Getter
@Setter
public class PatientChartNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "patient_case_uuid")
    private String patientCaseUuid;

    @Column(name = "note_id")
    private String noteId;

    @Column(name = "note_status")
    @Setter(lombok.AccessLevel.NONE)
    private String noteStatus;

    public void setNoteStatus(String noteStatus) {
        this.noteStatus = noteStatus;
        this.updatedAt = LocalDateTime.now();
    }

    @Column(name = "note_type")
    private String noteType;

    @Column(name = "encounter_date")
    private LocalDateTime encounterDate;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
