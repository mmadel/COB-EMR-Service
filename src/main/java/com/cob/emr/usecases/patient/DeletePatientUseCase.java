package com.cob.emr.usecases.patient;

import com.cob.emr.entity.appointment.Appointment;
import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.repositories.appointment.AppointmentRepository;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DeletePatientUseCase {
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    ClinicRepository clinicRepository;

    public void delete(Long id) {
        deletePatientAppointments(id);
        patientRepository.deleteById(id);
    }

    public void deleteByClinic(Clinic clinic) {
        Clinic attachedClinic = clinicRepository.findById(clinic.getId()).get();
        attachedClinic.getPatients()
                .stream()
                .forEach(patient -> {
                    delete(patient.getId());
                });
    }

    private void deletePatientAppointments(Long patientId) {
        List<Appointment> appointments = appointmentRepository.findByPatient_Id(patientId).get();
        appointmentRepository.deleteAllById(appointments.stream()
                .map(appointment -> appointment.getId())
                .collect(Collectors.toList()));
    }

}
