package com.cob.emr.entity.patient;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "patient_payment_record")
@Getter
@Setter
public class PatientPaymentRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "amount")
    private double amount;

    @Column(name = "reason")
    private String reason;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    @Column(name = "created_at")
    private Long createdAt;

    @PrePersist
    private void setCreatedDate() {
        createdAt = new Date().getTime();
    }

}
