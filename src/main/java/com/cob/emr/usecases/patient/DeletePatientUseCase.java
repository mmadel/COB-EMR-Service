package com.cob.emr.usecases.patient;

import com.cob.emr.entity.appointment.Appointment;
import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.patient.Patient;
import com.cob.emr.repositories.appointment.AppointmentRepository;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.patient.PatientRepository;
import com.cob.emr.repositories.patient.cases.PatientCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
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
    @Autowired
    PatientCaseRepository patientCaseRepository;

    @Transactional
    public void delete(Long id) {
        deletePatientAppointments(id);
        deletePatientCases(id);
        patientRepository.deleteById(id);
    }

    public void deleteByClinic(Long clinicId) {
        Clinic attachedClinic = clinicRepository.findById(clinicId).get();
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

    private void deletePatientCases(Long patientId) {
        Patient patient = patientRepository.findById(patientId).get();
        patientCaseRepository.deleteByPatient(patient);
    }
}
