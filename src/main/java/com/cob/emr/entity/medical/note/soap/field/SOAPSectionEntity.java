package com.cob.emr.entity.medical.note.soap.field;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "soap_section")
@Getter
@Setter
public class SOAPSectionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", referencedColumnName = "id")
    private List<NewSOAPSectionCategoryEntity> categories;
}
