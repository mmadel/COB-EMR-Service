package com.cob.emr.usecases.appointment.cancelnoshow;

import com.cob.emr.entity.appointment.Appointment;
import com.cob.emr.entity.appointment.AppointmentCancelNoShowReason;
import com.cob.emr.entity.patient.PatientCancellationFee;
import com.cob.emr.enums.AppointmentStatus;
import com.cob.emr.model.appointment.AppointmentCancelNoShowReasonModel;
import com.cob.emr.model.patient.PatientCancellationFeeModel;
import com.cob.emr.model.patient.payment.PatientPaymentRecordRequest;
import com.cob.emr.repositories.appointment.AppointmentCancelNoShowReasonRepository;
import com.cob.emr.repositories.appointment.AppointmentRepository;
import com.cob.emr.repositories.patient.cancellation.fee.PatientCancellationFeeRepository;
import com.cob.emr.usecases.appointment.patient.payment.CreatePatientPaymentRecordUseCase;
import com.cob.emr.usecases.patient.cancellation.fee.ApplyCancellationFeeUseCase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
public class UpdateAppointmentCancelNoShowUseCase {
    @Autowired
    AppointmentCancelNoShowReasonRepository repository;
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    ModelMapper mapper;
    @Autowired
    PatientCancellationFeeRepository patientCancellationFeeRepository;
    @Autowired
    ApplyCancellationFeeUseCase applyCancellationFeeUseCase;
    @Autowired
    CreatePatientPaymentRecordUseCase createPatientPaymentRecordUseCase;

    @Transactional
    public void update(AppointmentCancelNoShowReasonModel model) {
        Appointment appointment = appointmentRepository.findById(model.getAppointmentId())
                .orElseThrow(() -> new IllegalArgumentException("Appointment with id " + model.getAppointmentId() + " not found "));
        AppointmentCancelNoShowReason entity;
        Optional<AppointmentCancelNoShowReason> optional = repository.findByAppointment_Id(model.getAppointmentId());
        if (optional.isPresent())
            entity = optional.get();
        else
            entity = mapper.map(model, AppointmentCancelNoShowReason.class);
        appointment.setAppointmentStatus(model.getStatus());
        repository.save(entity);
        deAttachFromSeries(appointment.getId(),appointment.getSeriesId(),appointment);
        appointmentRepository.save(appointment);
        if (model.getStatus().equals(AppointmentStatus.Cancel))
            applyFee(appointment.getStartDate(), model.getReasonDate(), appointment.getClinic().getId(), appointment.getPatient().getId());
    }

    private void applyFee(Long appointmentStartDate, Long appointmentCancelDate, Long clinicId, Long patientId) {
        PatientCancellationFee patientCancellationFee = patientCancellationFeeRepository.findByClinic_Id(clinicId)
                .orElseThrow(() -> new IllegalArgumentException("Cancellation Fee Rule not found for  clinic : " + clinicId));
        PatientCancellationFeeModel rule = mapper.map(patientCancellationFee, PatientCancellationFeeModel.class);
        double fee = applyCancellationFeeUseCase.apply(appointmentStartDate, appointmentCancelDate, rule);
        if (fee != 0.0)
            createPatientPaymentRecord(patientId, fee, "Cancellation Fee");
    }

    private void createPatientPaymentRecord(Long patientId, double amount, String reason) {
        PatientPaymentRecordRequest request = PatientPaymentRecordRequest.builder()
                .patientId(patientId)
                .amount(amount)
                .reason(reason)
                .build();
        createPatientPaymentRecordUseCase.create(request);
    }
    private void deAttachFromSeries(Long appointmentId , Integer seriesId , Appointment appointment){
        if(appointmentId != seriesId.longValue()){
            appointment.setSeriesId(appointmentId.intValue());
        }
    }
}
