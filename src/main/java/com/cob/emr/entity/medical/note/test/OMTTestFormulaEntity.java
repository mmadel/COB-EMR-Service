package com.cob.emr.entity.medical.note.test;

import javax.persistence.*;

@Entity
@Table(name = "omt_test_formula")
public class OMTTestFormulaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "formula_name")
    private String formulaName;

    @Column(name = "formula")
    private String formula;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "omt_test_id")
    private OMTTestEntity test;
}
