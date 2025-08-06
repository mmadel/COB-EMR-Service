package com.cob.emr.model.patient.payment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class PatientPaymentRecordRequest {
    private Long patientId;
    private double amount;
    private String reason;
}
