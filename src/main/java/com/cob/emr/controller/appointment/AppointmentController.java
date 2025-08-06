package com.cob.emr.controller.appointment;

import com.cob.emr.enums.AppointmentStatus;
import com.cob.emr.model.appointment.AppointmentCancelNoShowReasonModel;
import com.cob.emr.model.appointment.AppointmentFilterModel;
import com.cob.emr.model.appointment.SchedulerAppointment;
import com.cob.emr.response.ResponseHandler;
import com.cob.emr.service.appointment.AppointmentFinderService;
import com.cob.emr.service.appointment.history.AppointmentHistoryFinderService;
import com.cob.emr.usecases.appointment.FindFullAppointmentUseCase;
import com.cob.emr.usecases.appointment.cancelnoshow.UpdateAppointmentCancelNoShowUseCase;
import com.cob.emr.usecases.appointment.create.CreateSchedulerAppointment;
import com.cob.emr.usecases.appointment.patient.FindAppointmentPatientUseCase;
import com.cob.emr.usecases.appointment.update.UpdateAppointmentStatusUseCase;
import com.cob.emr.usecases.appointment.update.UpdateSingleSchedulerAppointmentUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    AppointmentFinderService finderService;

    @Autowired
    AppointmentHistoryFinderService historyFinderService;

    @Autowired
    FindFullAppointmentUseCase findFullAppointmentUseCase;
    @Autowired
    UpdateAppointmentCancelNoShowUseCase updateAppointmentCancelNoShowUseCase;
    @Autowired
    FindAppointmentPatientUseCase findAppointmentPatientUseCase;
    @Autowired
    @Qualifier("CreateSingleSchedulerAppointment")
    CreateSchedulerAppointment createSchedulerAppointment;

    @Autowired
    UpdateSingleSchedulerAppointmentUseCase updateSingleSchedulerAppointmentUseCase;
    @Autowired
    UpdateAppointmentStatusUseCase updateAppointmentStatusUseCase;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody SchedulerAppointment model) {
        //dummy comment-3
        return ResponseEntity.ok()
                .body(createSchedulerAppointment.create(model));
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestBody SchedulerAppointment model) {
        return ResponseEntity.ok()
                .body(updateSingleSchedulerAppointmentUseCase.update(model));
    }

    @PutMapping("/update/status")
    public ResponseEntity updateStatus(@RequestBody SchedulerAppointment model) {
        updateAppointmentStatusUseCase.update(model);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/find/startDate/{startDate}/endDate/{endDate}/{clinicId}/calendarId/{calendarId}")
    public ResponseEntity<Object> find(@PathVariable Long startDate, @PathVariable Long endDate,
                                       @PathVariable Long clinicId,
                                       @PathVariable Long calendarId,
                                       @RequestParam(required = false) List<AppointmentStatus> statuses) {
        return ResponseHandler
                .generateResponse("Successfully find Appointment by date range",
                        HttpStatus.OK,
                        finderService.find(startDate, endDate, clinicId, calendarId,statuses));

    }

    @PostMapping(path = "/filter/{startDate}/{endDate}/{clinicId}")
    public ResponseEntity<Object> filter(@PathVariable long startDate, @PathVariable long endDate,
                                         @PathVariable long clinicId,
                                         @RequestBody AppointmentFilterModel filters) {
        return ResponseHandler
                .generateResponse("Successfully filter appointment by filters inputs",
                        HttpStatus.OK,
                        null);

    }

    @GetMapping("/history/id/{appointmentId}")
    public ResponseEntity<Object> ListAppointmentHistory(@PathVariable Long appointmentId) {
        return ResponseHandler
                .generateResponse("Successfully filter appointment by filters inputs",
                        HttpStatus.OK,
                        historyFinderService.find(appointmentId));
    }

    @GetMapping("/find/id/{appointmentId}")
    public ResponseEntity find(@PathVariable Long appointmentId) {
        return ResponseEntity.ok()
                .body(finderService.find(appointmentId));
    }

    @GetMapping("/find/full/id/{appointmentId}")
    public ResponseEntity findFullAppointment(@PathVariable Long appointmentId) {
        return ResponseEntity.ok()
                .body(findFullAppointmentUseCase.find(appointmentId));
    }

    @PutMapping("/update/cancel-noshow")
    public ResponseEntity updateAppointmentCancelNoShow(@RequestBody AppointmentCancelNoShowReasonModel model) {
        updateAppointmentCancelNoShowUseCase.update(model);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/patient/appointment-id/{appointmentId}")
    public ResponseEntity getAppointmentPatient(@PathVariable Long appointmentId) {
        return ResponseEntity.ok()
                .body(findAppointmentPatientUseCase.findPatient(appointmentId));
    }

    @GetMapping("/patient-case/appointment-id/{appointmentId}")
    public ResponseEntity getAppointmentPatientCase(@PathVariable Long appointmentId) {
        return ResponseEntity.ok()
                .body(findAppointmentPatientUseCase.findPatientCase(appointmentId));
    }
}
