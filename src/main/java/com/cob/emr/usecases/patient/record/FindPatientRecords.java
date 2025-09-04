package com.cob.emr.usecases.patient.record;

import com.cob.emr.Utils.PaginationUtil;
import com.cob.emr.entity.appointment.Appointment;
import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.enums.MedicalNoteStatus;
import com.cob.emr.enums.PatientRecordLineAction;
import com.cob.emr.model.patient.record.PatientRecord;
import com.cob.emr.model.patient.record.PatientRecordRequest;
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
                    List<String> actions = new ArrayList<>();
                    actions.add(PatientRecordLineAction.View_Reason.label);
                    actions.add(PatientRecordLineAction.View_PDF.label);
                    patientRecord.setActions(actions);
                    return patientRecord;
                }).collect(Collectors.toList()));
    }

    private void getNotes(Long caseId, List<PatientRecord> records) {
        List<MedicalNote> medicalNotes = medicalNoteRepository.findByPatientCase_Id(caseId).orElseThrow(() -> new IllegalArgumentException("Case is not found "));
        records.addAll(medicalNotes.stream()
                .filter(medicalNote -> !medicalNote.getStatus().equals(MedicalNoteStatus.Remove))
                .map(medicalNote -> {
                    PatientRecord patientRecord = new PatientRecord();
                    patientRecord.setEntityId(medicalNote.getId());
                    patientRecord.setStatus(medicalNote.getType().label);
                    patientRecord.setNote(true);
                    patientRecord.setDate(medicalNote.getCreatedAt());
                    patientRecord.setActions(addPatientLineRecordAction(medicalNote.getStatus()));
                    return patientRecord;
                }).collect(Collectors.toList()));
    }

    private void getInitialExaminationScore() {

    }

    private void getInitialExaminationPlanOfCare() {

    }

    private List<String> addPatientLineRecordAction(MedicalNoteStatus status) {
        List<String> actions = new ArrayList<>();
        switch (status) {
            case Create:
            case Draft:
                actions.add(PatientRecordLineAction.Complete.label);
                actions.add(PatientRecordLineAction.Remove.label);
                break;
            case Froward:
                System.out.println("Froward");
                break;
            case Finalized:
                actions.add(PatientRecordLineAction.View_PDF.label);
                break;
        }
        return actions;
    }
}
