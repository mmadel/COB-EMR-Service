package com.cob.emr.usecases.appointment.patient.payment;

import com.cob.emr.entity.patient.PatientPaymentRecord;
import com.cob.emr.model.patient.payment.PatientPaymentRecordChart;
import com.cob.emr.model.response.PatientPaymentRecordResponse;
import com.cob.emr.repositories.patient.payment.PatientPaymentRecordRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindPatientPaymentRecordsUseCase {
    @Autowired
    PatientPaymentRecordRepository patientPaymentRecordRepository;
    @Autowired
    ModelMapper mapper;

    public PatientPaymentRecordResponse find(Long patientId,Long patientCaseId, Pageable pageable) {
        Page<PatientPaymentRecord> pages = patientPaymentRecordRepository.findByPatient_IdAndPatientCase_Id(patientId,patientCaseId, pageable);
        long total = (pages).getTotalElements();
        List<PatientPaymentRecordChart> records = pages.stream().map(patientPaymentRecord -> {
                    PatientPaymentRecordChart paymentRecordChart = new PatientPaymentRecordChart();
                    paymentRecordChart.setAmount(patientPaymentRecord.getAmount());
                    paymentRecordChart.setReason(patientPaymentRecord.getReason());
                    paymentRecordChart.setCreatedAt(patientPaymentRecord.getCreatedAt());
                    return paymentRecordChart;
                })
                .collect(Collectors.toList());

        return PatientPaymentRecordResponse.builder()
                .number_of_records(records.size())
                .number_of_matching_records((int) total)
                .records(records)
                .build();
    }
}
