package com.cob.emr.entity.patient;

import com.cob.emr.entity.security.user.ClinicalEMRUser;
import com.cob.emr.enums.ChargeType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "patient_case_payment")
@Getter
@Setter
public class PatientCasePayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid", unique = true, nullable = false, updatable = false)
    private String uuid;

    @Column(name = "date_of_transaction")
    private LocalDate dateOfTransaction;

    @Column(name = "payment_method")
    private String paymentMethod;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", referencedColumnName = "id")
    private ClinicalEMRUser provider;

    @Column(name = "date_of_service")
    private LocalDate dateOfService;

    @Enumerated(EnumType.STRING)
    @Column(name = "charge_type")
    private ChargeType chargeType;

    @Column(name = "amount_due", precision = 10, scale = 2)
    private BigDecimal amountDue;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_case_id")
    private PatientCase patientCase;

    @PrePersist
    public void generateUuid() {
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
        }
    }
}
