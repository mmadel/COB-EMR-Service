package com.cob.emr.model.patient.insurance;

import com.cob.emr.enums.PaymentType;
import com.cob.emr.model.insurancecompnay.InsuranceCompanyModel;
import com.cob.emr.model.patient.PatientDependencyModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class PatientInsuranceDependencyModel implements PatientDependencyModel {

    private String insuranceNumber;
    private String groupNumber;
    private PaymentType paymentType;
    private String paymentValue;
    private String totalDeductible;
    private Integer visitAllowed;
    private Long expirationDate;
    private InsuranceCompanyModel insuranceCompany;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatientInsuranceDependencyModel that = (PatientInsuranceDependencyModel) o;
        return Objects.equals(insuranceNumber, that.insuranceNumber) && Objects.equals(groupNumber, that.groupNumber) && paymentType == that.paymentType && Objects.equals(paymentValue, that.paymentValue) && Objects.equals(totalDeductible, that.totalDeductible) && Objects.equals(visitAllowed, that.visitAllowed) && Objects.equals(expirationDate, that.expirationDate) && Objects.equals(insuranceCompany, that.insuranceCompany);
    }

    @Override
    public int hashCode() {
        return Objects.hash(insuranceNumber, groupNumber, paymentType, paymentValue, totalDeductible, visitAllowed, expirationDate, insuranceCompany);
    }
}
