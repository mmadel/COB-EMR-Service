package com.cob.emr.usecases.patient.record;

import com.cob.emr.entity.appointment.Appointment;
import com.cob.emr.model.patient.record.PatientRecord;
import com.cob.emr.model.patient.record.PatientRecordRequest;
import com.cob.emr.repositories.appointment.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindPatientRecords {
    @Autowired
    AppointmentRepository appointmentRepository;

    public List<PatientRecord> construct(PatientRecordRequest request) {
        List<PatientRecord> records = new ArrayList<>();
        getCancelAppointments(request.getPatientId(), request.getClinicId(), request.getCaseId(), records);
        return records;
    }

    private void getCancelAppointments(long patientId, long clinicId, long patientCaseId, List<PatientRecord> records) {
        List<Appointment> appointments = appointmentRepository.findAllNoShowAppointmentsWithoutPage(patientId, clinicId, patientCaseId);
        records.addAll(appointments.stream()
                .map(appointment -> {
                    PatientRecord patientRecord = new PatientRecord();
                    patientRecord.setEntityId(appointment.getId());
                    patientRecord.setStatus(appointment.getAppointmentStatus().toString());
                    patientRecord.setDate(appointment.getStartDate());
                    return patientRecord;
                }).collect(Collectors.toList()));
    }

    private void getNotes() {

    }
}
