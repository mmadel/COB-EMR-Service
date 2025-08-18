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

    @ManyToMany(mappedBy = "noteTypes")
    private Set<NewSOAPFieldEntity> fields = new HashSet<>();
}
