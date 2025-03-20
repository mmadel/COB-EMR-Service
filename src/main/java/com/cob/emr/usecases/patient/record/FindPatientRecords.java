package com.cob.emr.usecases.patient.record;

import com.cob.emr.Utils.PaginationUtil;
import com.cob.emr.entity.appointment.Appointment;
import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.model.calendar.CalendarsListModel;
import com.cob.emr.model.patient.record.PatientRecord;
import com.cob.emr.model.patient.record.PatientRecordRequest;
import com.cob.emr.model.response.CalendarResponse;
import com.cob.emr.model.response.patient.record.PatientRecordResponse;
import com.cob.emr.repositories.appointment.AppointmentRepository;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindPatientRecords {
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    MedicalNoteRepository medicalNoteRepository;

    public PatientRecordResponse construct(PatientRecordRequest request, int offset, int limit) {
        List<PatientRecord> patientRecords = new ArrayList<>();
        getCancelAppointments(request.getPatientId(), request.getClinicId(), request.getCaseId(), patientRecords);
        getNotes(request.getCaseId(), patientRecords);
        List<PatientRecord> records = PaginationUtil.paginate(patientRecords, offset, limit);
        return PatientRecordResponse.builder()
                .number_of_records(records.size())
                .number_of_matching_records(patientRecords.size())
                .records(records)
                .build();
    }

    private void getCancelAppointments(long patientId, long clinicId, long patientCaseId, List<PatientRecord> records) {
        List<Appointment> appointments = appointmentRepository.findAllNoShowAppointmentsWithoutPage(patientId, clinicId, patientCaseId);
        records.addAll(appointments.stream()
                .map(appointment -> {
                    PatientRecord patientRecord = new PatientRecord();
                    patientRecord.setEntityId(appointment.getId());
                    patientRecord.setStatus(appointment.getAppointmentStatus().label);
                    patientRecord.setDate(appointment.getStartDate());
                    return patientRecord;
                }).collect(Collectors.toList()));
    }

    private void getNotes(Long caseId, List<PatientRecord> records) {
        List<MedicalNote> medicalNotes = medicalNoteRepository.findByPatientCase_Id(caseId).orElseThrow(() -> new IllegalArgumentException("Case is not found "));
        records.addAll(medicalNotes.stream()
                .map(medicalNote -> {
                    PatientRecord patientRecord = new PatientRecord();
                    patientRecord.setEntityId(medicalNote.getId());
                    patientRecord.setStatus(medicalNote.getType().label);
                    patientRecord.setDate(medicalNote.getCreatedAt());
                    return patientRecord;
                }).collect(Collectors.toList()));
        System.out.println("");
    }
}
