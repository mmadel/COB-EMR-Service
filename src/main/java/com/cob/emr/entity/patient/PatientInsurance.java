package com.cob.emr.entity.patient;

import com.cob.emr.entity.insurance.InsuranceCompany;
import com.cob.emr.enums.PaymentType;
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

    @Column(name = "insurance_number")
    private String insuranceNumber;

    @Column(name = "group_number")
    private String groupNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private PaymentType paymentType;

    @Column(name = "payment_value")
    private String paymentValue;

    @Column(name = "total_deductible")
    private String totalDeductible;

    @Column(name = "visit_allowed")
    private Integer visitAllowed;

    @Column(name = "expiration_date")
    private Long expirationDate;

    @ManyToOne
    @JoinColumn(name="insurance_company_id")
    private InsuranceCompany insuranceCompany;
}
