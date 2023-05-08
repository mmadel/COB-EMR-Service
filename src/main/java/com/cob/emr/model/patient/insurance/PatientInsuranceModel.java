package com.cob.emr.model.patient.insurance;

import com.cob.emr.enums.PaymentType;
import com.cob.emr.model.insurancecompnay.InsuranceCompanyModel;
import lombok.Getter;
import lombok.Setter;

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

    private InsuranceCompanyModel insuranceCompany;

}
