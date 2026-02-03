package com.cob.emr.model.patient;

import com.cob.emr.enums.ChargeType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
public class PatientCasePaymentModel {
    private Long id;
    private String uuid;
    private LocalDate dateOfTransaction;
    private String paymentMethod;
    private Long providerId;
    private LocalDate dateOfService;
    private ChargeType chargeType;
    private BigDecimal amountDue;
    private String description;
    private Boolean paid;
    private Long patientCaseId;
}
