package com.cob.emr.usecases.appointment.cancelnoshow;

import com.cob.emr.entity.appointment.Appointment;
import com.cob.emr.entity.appointment.AppointmentCancelNoShowReason;
import com.cob.emr.entity.patient.PatientCancellationFee;
import com.cob.emr.enums.AppointmentStatus;
import com.cob.emr.model.appointment.AppointmentCancelNoShowReasonModel;
import com.cob.emr.model.patient.PatientCancellationFeeModel;
import com.cob.emr.repositories.appointment.AppointmentCancelNoShowReasonRepository;
import com.cob.emr.repositories.appointment.AppointmentRepository;
import com.cob.emr.repositories.patient.cancellation.fee.PatientCancellationFeeRepository;
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
        appointmentRepository.save(appointment);
        if (model.getStatus().equals(AppointmentStatus.Cancel))
            applyFee(appointment.getStartDate(), model.getReasonDate(), appointment.getClinic().getId());
    }

    private void applyFee(Long appointmentStartDate, Long appointmentCancelDate, Long clinicId) {
        PatientCancellationFee patientCancellationFee = patientCancellationFeeRepository.findByClinic_Id(clinicId)
                .orElseThrow(() -> new IllegalArgumentException("Cancellation Fee Rule not found for  clinic : " + clinicId));
        PatientCancellationFeeModel rule = mapper.map(patientCancellationFee, PatientCancellationFeeModel.class);
        double fee = applyCancellationFeeUseCase.apply(appointmentStartDate, appointmentCancelDate, rule);
        if (fee != 0.0)
            createPatientPaymentRecord();
    }

    private void createPatientPaymentRecord() {

    }
}
