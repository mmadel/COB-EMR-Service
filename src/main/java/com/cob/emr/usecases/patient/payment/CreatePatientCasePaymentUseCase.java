package com.cob.emr.usecases.patient.payment;

import com.cob.emr.entity.patient.PatientCase;
import com.cob.emr.entity.patient.PatientCasePayment;
import com.cob.emr.exception.business.PatientCaseException;
import com.cob.emr.model.patient.PatientCasePaymentModel;
import com.cob.emr.repositories.patient.cases.PatientCaseRepository;
import com.cob.emr.repositories.patient.payment.PatientCasePaymentRepository;
import com.cob.emr.repositories.security.ClinicalEMRUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CreatePatientCasePaymentUseCase {
    @Autowired
    ModelMapper mapper;
    @Autowired
    PatientCasePaymentRepository patientCasePaymentRepository;
    @Autowired
    PatientCaseRepository patientCaseRepository;
    @Autowired
    ClinicalEMRUserRepository clinicalEMRUserRepository;

    @Transactional
    public List<PatientCasePaymentModel> create(Long patientCaseId, List<PatientCasePaymentModel> models) throws PatientCaseException {
        PatientCase patientCase = patientCaseRepository.findById(patientCaseId)
                .orElseThrow(() -> new PatientCaseException(HttpStatus.NOT_FOUND, PatientCaseException.CASE_NOT_FOUND, new Object[]{patientCaseId}));

        List<PatientCasePayment> payments = models.stream()
                .map(model -> {
                    PatientCasePayment payment = mapper.map(model, PatientCasePayment.class);
                    payment.setPatientCase(patientCase);
                    if (model.getProviderId() != null) {
                        clinicalEMRUserRepository.findById(model.getProviderId())
                                .ifPresent(payment::setProvider);
                    }
                    return payment;
                })
                .collect(Collectors.toList());

        List<PatientCasePayment> savedPayments = patientCasePaymentRepository.saveAll(payments);

        return savedPayments.stream()
                .map(payment -> {
                    PatientCasePaymentModel savedModel = mapper.map(payment, PatientCasePaymentModel.class);
                    savedModel.setPatientCaseId(patientCaseId);
                    if (payment.getProvider() != null) {
                        savedModel.setProviderId(payment.getProvider().getUuid());
                    }
                    return savedModel;
                })
                .collect(Collectors.toList());
    }
}
