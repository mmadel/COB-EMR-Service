package com.cob.emr.model.patient.payment;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientPaymentRecordChart {
    private double amount;
    private String reason;
    private Long createdAt;
}
