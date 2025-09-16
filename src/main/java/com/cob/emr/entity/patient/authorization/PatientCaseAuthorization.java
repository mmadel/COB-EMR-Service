package com.cob.emr.entity.patient.authorization;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "patient_case_authorization")
@Setter
@Getter
public class PatientCaseAuthorization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Basic auth fields
    @Column(name = "auth_name", nullable = false)
    private String authName;

    @Column(name = "auth_type")
    private String authType;

    @Column(name = "auth_number")
    private Integer authNumber;

    // Dates stored as milliseconds
    @Column(name = "effective_start")
    private Long effectiveStart;

    @Column(name = "effective_end")
    private Long effectiveEnd;

    // Insurance fields embedded inside the entity (no separate table)
    @Column(name = "insurance_name")
    private String insuranceName;

    @Column(name = "insurance_id")
    private Long insuranceId;

    // Constructors
    public PatientCaseAuthorization() {}
}
