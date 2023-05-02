package com.cob.emr.entity.patient;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "patient_case")
@Getter
@Setter
public class PatientCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
}