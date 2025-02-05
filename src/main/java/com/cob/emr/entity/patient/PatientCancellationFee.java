package com.cob.emr.entity.patient;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.enums.CancellationFeeDurationUnit;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "patient_cancellation_fee")
@Getter
@Setter
public class PatientCancellationFee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "amount")
    private Double amount;
    @Column(name = "duration")
    private Integer duration;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Enumerated(EnumType.STRING)
    @Column(name = "fee_duration_unit")
    private CancellationFeeDurationUnit feeDurationUnit;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_id", referencedColumnName = "id")
    private Clinic clinic;
}
