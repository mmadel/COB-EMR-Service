package com.cob.emr.usecases.appointment;

import com.cob.emr.entity.appointment.Appointment;
import com.cob.emr.model.appointment.FullAppointmentModel;
import com.cob.emr.model.clinic.ClinicModel;
import com.cob.emr.model.patient.PatientModel;
import com.cob.emr.model.patient.cases.PatientCaseModel;
import com.cob.emr.repositories.appointment.AppointmentRepository;
import com.cob.emr.repositories.patient.PatientRepository;
import com.cob.emr.repositories.patient.cases.PatientCaseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class FindFullAppointmentUseCase {
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    PatientCaseRepository patientCaseRepository;
    @Autowired
    ModelMapper mapper;

    public FullAppointmentModel find(Long id){
        Appointment appointment =appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found "));
        FullAppointmentModel model = mapper.map(appointment,FullAppointmentModel.class);
        model.setPatient(mapper.map(appointment.getPatient(), PatientModel.class));
        model.setPatientCase(mapper.map(appointment.getPatientCase(), PatientCaseModel.class));
        model.getPatient().setClinicModels(
                appointment.getPatient().getClinics().stream()
                        .map(clinicEntity -> mapper.map(clinicEntity , ClinicModel.class))
                        .collect(Collectors.toList())
        );
        return model;
    }
}
