package com.cob.emr.usecases.patient;

import com.cob.emr.entity.appointment.Appointment;
import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.patient.Patient;
import com.cob.emr.repositories.appointment.AppointmentRepository;
import com.cob.emr.repositories.appointment.AppointmentTypeRepository;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.patient.PatientRepository;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import com.cob.emr.repositories.patient.authorization.PatientCaseAuthorizationRepository;
import com.cob.emr.repositories.patient.cancellation.fee.PatientCancellationFeeRepository;
import com.cob.emr.repositories.patient.cases.PatientCaseRepository;
import com.cob.emr.repositories.patient.payment.PatientPaymentRecordRepository;
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
    @Autowired
    PatientPaymentRecordRepository patientPaymentRecordRepository;
    @Autowired
    PatientCaseAuthorizationRepository patientCaseAuthorizationRepository;
    @Autowired
    MedicalNoteRepository medicalNoteRepository;
    @Autowired
    PatientCancellationFeeRepository patientCancellationFeeRepository;

    @Transactional
    public void delete(Long id) {
        deletePatientAppointments(id);
        deletePatientPaymentRecords(id);
        deletePatientCaseAuthorizations(id);
        deleteMedicalNotes(id);
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
        appointmentRepository.deleteByClinic_Id(clinicId);
        patientCancellationFeeRepository.deleteByClinicId(clinicId);
    }

    private void deletePatientAppointments(Long patientId) {
        List<Appointment> appointments = appointmentRepository.findByPatient_Id(patientId).get();
        appointmentRepository.deleteAllById(appointments.stream()
                .map(appointment -> appointment.getId())
                .collect(Collectors.toList()));
    }


    private void deletePatientPaymentRecords(Long patientId) {
        patientPaymentRecordRepository.deleteByPatient_Id(patientId);
    }

    private void deletePatientCaseAuthorizations(Long patientId) {
        patientCaseAuthorizationRepository.deleteByPatientCase_Patient_Id(patientId);
    }

    private void deleteMedicalNotes(Long patientId) {
        medicalNoteRepository.deleteByPatientCase_Patient_Id(patientId);
    }

    private void deletePatientCases(Long patientId) {
        patientCaseRepository.deleteByPatient_Id(patientId);
    }
}
