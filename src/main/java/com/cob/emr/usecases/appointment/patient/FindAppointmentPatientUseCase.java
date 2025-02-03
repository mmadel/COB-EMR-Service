package com.cob.emr.usecases.appointment.patient;

import com.cob.emr.entity.patient.Patient;
import com.cob.emr.model.clinic.ClinicModel;
import com.cob.emr.model.patient.PatientModel;
import com.cob.emr.model.patient.cases.PatientCaseModel;
import com.cob.emr.repositories.appointment.AppointmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindAppointmentPatientUseCase {
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    ModelMapper mapper;

    public PatientModel findPatient(Long appointmentId) {
        Patient patient = appointmentRepository.findPatientByAppointment(appointmentId);
        List<ClinicModel> clinicModels = patient.getClinics().stream()
                .map(clinicEntity -> mapper.map(clinicEntity, ClinicModel.class))
                .collect(Collectors.toList());
        PatientModel model = mapper.map(appointmentRepository.findPatientByAppointment(appointmentId), PatientModel.class);
        model.setClinicModels(clinicModels);
        return model;
    }

    public PatientCaseModel findPatientCase(Long appointmentId) {
        return mapper.map(appointmentRepository.findPatientCaseByAppointment(appointmentId), PatientCaseModel.class);
    }
}
