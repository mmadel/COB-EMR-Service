package com.cob.emr.entity.medical.note;

import com.cob.emr.entity.patient.PatientCase;
import com.cob.emr.enums.MedicalNoteStatus;
import com.cob.emr.enums.MedicalNoteType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "medical_note")
@Setter
@Getter
public abstract class MedicalNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "case_id")
    private PatientCase patientCase;

    @Enumerated(EnumType.STRING)
    @Column(name = "note_type")
    private MedicalNoteType type;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_at")
    private Long updatedAt;

    @Column(name = "note_date")
    private Long noteDate;

    @Enumerated(EnumType.STRING)
    private MedicalNoteStatus status;

    @PrePersist
    private void beforeSaving() {
        createdAt = new Date().getTime();
    }
    @PreUpdate
    private void beforeUpdating() {
        updatedAt = new Date().getTime();
    }
}
