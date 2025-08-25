package com.cob.emr.entity.medical.note.test;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "omt_test")
@Setter
@Getter
public class OMTTestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="test_name")
    private String testName;

    @Column(name="test_description")
    private String testDescription;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OMTTestFormulaEntity> formulas = new ArrayList<>();
}
