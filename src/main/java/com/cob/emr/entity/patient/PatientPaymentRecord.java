package com.cob.emr.entity.patient;

import com.cob.emr.entity.clinic.Clinic;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_case_id", referencedColumnName = "id")
    private PatientCase patientCase;

    @Column(name = "created_at")
    private Long createdAt;

    @PrePersist
    private void setCreatedDate() {
        createdAt = new Date().getTime();
    }

}
