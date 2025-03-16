package com.cob.emr.entity.medical.note;

import com.cob.emr.enums.MedicalNoteType;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "medical_note")
public class MedicalNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "note_type")
    private MedicalNoteType type;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "note_date")
    private Long noteDate;

    @Column(name = "status")
    private String status;
}
