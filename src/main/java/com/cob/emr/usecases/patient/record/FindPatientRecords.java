package com.cob.emr.usecases.patient.record;

import com.cob.emr.Utils.PaginationUtil;
import com.cob.emr.entity.appointment.Appointment;
import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.entity.medical.note.finalize.init.exam.plan.InitialExaminationPlanOfCareEntity;
import com.cob.emr.entity.medical.note.finalize.init.exam.score.InitialExaminationScoreEntity;
import com.cob.emr.entity.patient.chart.note.PatientChartNote;
import com.cob.emr.enums.MedicalNoteStatus;
import com.cob.emr.enums.MedicalNoteType;
import com.cob.emr.enums.PatientRecordLineAction;
import com.cob.emr.model.patient.record.PatientRecord;
import com.cob.emr.model.patient.record.PatientRecordRequest;
import com.cob.emr.model.response.patient.record.PatientRecordResponse;
import com.cob.emr.repositories.appointment.AppointmentRepository;
import com.cob.emr.repositories.medical.note.InitialExaminationPlanOfCareRepository;
import com.cob.emr.repositories.medical.note.InitialExaminationScoreRepository;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import com.cob.emr.repositories.patient.chart.note.PatientChartNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FindPatientRecords {
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    MedicalNoteRepository medicalNoteRepository;
    @Autowired
    InitialExaminationScoreRepository initialExaminationScoreRepository;
    @Autowired
    InitialExaminationPlanOfCareRepository initialExaminationPlanOfCareRepository;
    @Autowired
    PatientChartNoteRepository patientChartNoteRepository;

    public PatientRecordResponse construct(PatientRecordRequest request, int offset, int limit) {
        List<PatientRecord> patientRecords = new ArrayList<>();
        getCancelAppointments(request.getPatientId(), request.getClinicId(), request.getCaseId(), patientRecords);
        getNotes(request.getCaseId(), patientRecords, request.getLoggedIn());
        List<PatientRecord> records = PaginationUtil.paginate(patientRecords, offset, limit)
                .stream()
                .sorted(Comparator.comparingLong(PatientRecord::getDate))
                .collect(Collectors.toList());
        return PatientRecordResponse.builder()
                .number_of_records(records.size())
                .number_of_matching_records(patientRecords.size())
                .records(records)
                .build();
    }

    private void getCancelAppointments(long patientId, long clinicId, String patientCaseId, List<PatientRecord> records) {
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

    private void getNotes(String caseId, List<PatientRecord> records, String loggedInUser) {
        List<PatientChartNote> chartNoteList = patientChartNoteRepository.findByPatientCaseUuid(caseId)
                .orElseThrow(() -> new IllegalArgumentException("Medical Note is not found for passed case"));
        records.addAll(chartNoteList.stream()
                .map(medicalNote -> {
                    PatientRecord patientRecord = new PatientRecord();
                    patientRecord.setEntityId(medicalNote.getId());
                    patientRecord.setNoteId(medicalNote.getNoteId());
                    patientRecord.setStatus(convertNoteType(medicalNote.getNoteType()));
                    patientRecord.setNote(true);
                    patientRecord.setDate(medicalNote.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
                    patientRecord.setActions(addPatientLineRecordAction(medicalNote.getNoteStatus()));
                    return patientRecord;
                }).collect(Collectors.toList()));

    }

    private boolean authorizedNote(String loggedInUser, MedicalNote note) {
        return (loggedInUser.equals(note.getCreatedBy()) || (note.getForwardTo() != null && loggedInUser.equals(note.getForwardTo())));
    }

    private void getInitialExaminationScore(Long noteId, List<PatientRecord> records) {
        final String INITIAL_EXAMINATION_SCORE = "Initial Examination Scores";
        Optional<InitialExaminationScoreEntity> optional = initialExaminationScoreRepository.findByInitialExaminationNoteId(noteId);
        if (optional.isPresent()) {
            InitialExaminationScoreEntity entity = optional.get();
            PatientRecord scores = createInitialExaminationRecord(entity.getId(), INITIAL_EXAMINATION_SCORE, entity.getCreatedAt());
            records.add(scores);
        }

    }

    private void getInitialExaminationPlanOfCare(Long noteId, List<PatientRecord> records) {
        final String INITIAL_EXAMINATION_PLAN_OF_CARE = "Initial Examination Plan Of Care";
        Optional<InitialExaminationPlanOfCareEntity> optional = initialExaminationPlanOfCareRepository.findByInitialExaminationNoteId(noteId);
        if (optional.isPresent()) {
            InitialExaminationPlanOfCareEntity entity = optional.get();
            PatientRecord planOfCare = createInitialExaminationRecord(entity.getId(), INITIAL_EXAMINATION_PLAN_OF_CARE, entity.getCreatedAt());
            records.add(planOfCare);
        }
    }

    private PatientRecord createInitialExaminationRecord(Long entityId, String status, Long createdAt) {
        PatientRecord patientRecord = new PatientRecord();
        patientRecord.setEntityId(entityId);
        patientRecord.setNote(false);
        List<String> actions = new ArrayList<>();
        actions.add(PatientRecordLineAction.View_PDF.label);
        patientRecord.setActions(actions);
        patientRecord.setStatus(status);
        patientRecord.setDate(createdAt);
        return patientRecord;
    }

    private List<String> addPatientLineRecordAction(String status) {
        List<String> actions = new ArrayList<>();
        switch (status) {
            case "CREATED":
            case "DRAFT":
                actions.add(PatientRecordLineAction.Complete.label);
                actions.add(PatientRecordLineAction.Remove.label);
                break;
//            case Froward:
//                System.out.println("Froward");
//                break;
            case "FINALIZED":
                actions.add(PatientRecordLineAction.View_PDF.label);
                break;
        }
        return actions;
    }

    private String convertNoteType(String noteType) {
        switch (noteType) {
            case "INITIAL_EXAM":
                return "Initial Examination";
            case "DAILY_NOTE":
                return "Daily Note";
            case "FOLLOW_UP":
                return "Follow Up";
            case "DISCHARGE":
                return "Discharge";
            default:
                throw new IllegalArgumentException("Note Type is not defined");
        }
    }
}
