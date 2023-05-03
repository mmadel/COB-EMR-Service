package com.cob.emr.model.patient.insurance;

import com.cob.emr.entity.insurance.InsuranceCompany;
import com.cob.emr.enums.PaymentType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Setter
@Getter
public class PatientInsuranceModel {

    private Long id;


    private String insuranceNumber;


    private String groupNumber;


    private PaymentType paymentType;


    private String paymentValue;


    private String totalDeductible;


    private Integer visitAllowed;


    private Long expirationDate;

    @OneToOne
    @JoinColumn(name = "insurance_company_id")
    private InsuranceCompany insuranceCompany;

}
