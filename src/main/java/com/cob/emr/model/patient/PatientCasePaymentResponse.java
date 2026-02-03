package com.cob.emr.model.patient;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@Builder
public class PatientCasePaymentResponse {
    private List<PatientCasePaymentModel> payments;
    private BigDecimal totalCharge;
    private BigDecimal totalPaid;
    private BigDecimal balance;
}
