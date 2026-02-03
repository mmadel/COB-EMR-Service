package com.cob.emr.usecases.patient.payment;

import com.cob.emr.entity.patient.PatientCasePayment;
import com.cob.emr.model.patient.PatientCasePaymentModel;
import com.cob.emr.model.patient.PatientCasePaymentResponse;
import com.cob.emr.repositories.patient.payment.PatientCasePaymentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindPatientCasePaymentUseCase {
    @Autowired
    ModelMapper mapper;
    @Autowired
    PatientCasePaymentRepository patientCasePaymentRepository;

    public PatientCasePaymentResponse findByPatientCase(Long patientCaseId) {
        List<PatientCasePayment> payments = patientCasePaymentRepository.findByPatientCase_Id(patientCaseId);

        List<PatientCasePaymentModel> paymentModels = payments.stream()
                .map(payment -> {
                    PatientCasePaymentModel model = mapper.map(payment, PatientCasePaymentModel.class);
                    model.setPatientCaseId(patientCaseId);
                    if (payment.getProvider() != null) {
                        model.setProviderId(payment.getProvider().getId());
                    }
                    return model;
                })
                .collect(Collectors.toList());

        BigDecimal totalCharge = payments.stream()
                .map(payment -> payment.getAmountDue() != null ? payment.getAmountDue() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPaid = payments.stream()
                .filter(payment -> Boolean.TRUE.equals(payment.getPaid()))
                .map(payment -> payment.getAmountDue() != null ? payment.getAmountDue() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal balance = payments.stream()
                .filter(payment -> !Boolean.TRUE.equals(payment.getPaid()))
                .map(payment -> payment.getAmountDue() != null ? payment.getAmountDue() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return PatientCasePaymentResponse.builder()
                .payments(paymentModels)
                .totalCharge(totalCharge)
                .totalPaid(totalPaid)
                .balance(balance)
                .build();
    }

    public List<PatientCasePaymentModel> findUnpaidByPatientCase(Long patientCaseId) {
        List<PatientCasePayment> payments = patientCasePaymentRepository.findByPatientCase_Id(patientCaseId);
        return payments.stream()
                .filter(payment -> !Boolean.TRUE.equals(payment.getPaid()))
                .map(payment -> {
                    PatientCasePaymentModel model = mapper.map(payment, PatientCasePaymentModel.class);
                    model.setPatientCaseId(patientCaseId);
                    if (payment.getProvider() != null) {
                        model.setProviderId(payment.getProvider().getId());
                    }
                    return model;
                })
                .collect(Collectors.toList());
    }
}
