package com.cob.emr.entity.medical.note.soap.field;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "soap_field_note_type")
@Getter
@Setter
public class SoapFieldNoteType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id")
    private NewSOAPFieldEntity field;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medical_note_id")
    private MedicalNoteTypeEntity noteType;

    @Column(name = "field_order")
    private Integer fieldOrder;
}
