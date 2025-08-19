package com.cob.emr.entity.medical.note.soap.field;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "medical_note_type")
@Setter
@Getter
public class MedicalNoteTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String type;

    @OneToMany(mappedBy = "noteType", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SoapFieldNoteType> fields = new HashSet<>();
}
