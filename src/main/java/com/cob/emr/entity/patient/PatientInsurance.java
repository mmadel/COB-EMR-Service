package com.cob.emr.entity.patient;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "patient_insurance")
@Getter
@Setter
public class PatientInsurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
}
