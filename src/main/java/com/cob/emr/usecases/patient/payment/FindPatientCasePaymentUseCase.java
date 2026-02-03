package com.cob.emr.usecases.patient.payment;

import com.cob.emr.entity.patient.PatientCasePayment;
import com.cob.emr.model.patient.PatientCasePaymentModel;
import com.cob.emr.repositories.patient.payment.PatientCasePaymentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindPatientCasePaymentUseCase {
    @Autowired
    ModelMapper mapper;
    @Autowired
    PatientCasePaymentRepository patientCasePaymentRepository;

    public List<PatientCasePaymentModel> findByPatientCase(Long patientCaseId) {
        List<PatientCasePayment> payments = patientCasePaymentRepository.findByPatientCase_Id(patientCaseId);
        return payments.stream()
                .map(payment -> {
                    PatientCasePaymentModel model = mapper.map(payment, PatientCasePaymentModel.class);
                    model.setPatientCaseId(patientCaseId);
                    if (payment.getProvider() != null) {
                        model.setProviderId(payment.getProvider().getUuid());
                    }
                    return model;
                })
                .collect(Collectors.toList());
    }
}
