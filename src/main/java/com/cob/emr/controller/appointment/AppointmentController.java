package com.cob.emr.controller.appointment;

import com.cob.emr.model.appointment.AppointmentCancelNoShowReasonModel;
import com.cob.emr.model.appointment.AppointmentFilterModel;
import com.cob.emr.model.appointment.AppointmentModel;
import com.cob.emr.response.ResponseHandler;
import com.cob.emr.service.appointment.AppointmentFinderService;
import com.cob.emr.service.appointment.history.AppointmentHistoryFinderService;
import com.cob.emr.usecases.UpdateAppointmentUseCase;
import com.cob.emr.usecases.appointment.FindFullAppointmentUseCase;
import com.cob.emr.usecases.appointment.cancelnoshow.UpdateAppointmentCancelNoShowUseCase;
import com.cob.emr.usecases.appointment.create.CreateAppointmentUseCase;
import com.cob.emr.usecases.appointment.series.FindBlockAppointmentSeriesUseCase;
import com.cob.emr.usecases.appointment.series.FindFullAppointmentSeriesUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    AppointmentFinderService finderService;

    @Autowired
    AppointmentHistoryFinderService historyFinderService;
    @Autowired
    CreateAppointmentUseCase createAppointmentUseCase;
    @Autowired
    UpdateAppointmentUseCase updateAppointmentUseCase;
    @Autowired
    FindFullAppointmentUseCase findFullAppointmentUseCase;
    @Autowired
    UpdateAppointmentCancelNoShowUseCase updateAppointmentCancelNoShowUseCase;
    @Autowired
    FindFullAppointmentSeriesUseCase findFullAppointmentSeriesUseCase;
    @Autowired
    FindBlockAppointmentSeriesUseCase findBlockAppointmentSeriesUseCase;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody AppointmentModel model) {
        return ResponseEntity.ok()
                .body(createAppointmentUseCase.execute(model));
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody AppointmentModel model) {
        updateAppointmentUseCase.update(model);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/find/startDate/{startDate}/endDate/{endDate}/{clinicId}/calendarId/{calendarId}")
    public ResponseEntity<Object> find(@PathVariable Long startDate, @PathVariable Long endDate,
                                       @PathVariable Long clinicId,
                                       @PathVariable Long calendarId) {
        return ResponseHandler
                .generateResponse("Successfully find Appointment by date range",
                        HttpStatus.OK,
                        finderService.find(startDate, endDate, clinicId, calendarId));

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
    public ResponseEntity<AppointmentModel> find(@PathVariable Long appointmentId) {
        return new ResponseEntity<>(finderService.find(appointmentId), HttpStatus.OK);
    }

    @GetMapping("/find/full/id/{appointmentId}")
    public ResponseEntity<AppointmentModel> findFullAppointment(@PathVariable Long appointmentId) {
        return ResponseEntity.ok()
                .body(findFullAppointmentUseCase.find(appointmentId));
    }

    @PutMapping("/update/cancel-noshow")
    public ResponseEntity updateAppointmentCancelNoShow(@RequestBody AppointmentCancelNoShowReasonModel model) {
        updateAppointmentCancelNoShowUseCase.update(model);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/series/clinicId/{clinicId}/seriesId/{seriesId}/type/{type}")
    public ResponseEntity getAppointmentSeries(@PathVariable Long clinicId, @PathVariable Integer seriesId, @PathVariable String type) {
        switch (type) {
            case "block":
                return ResponseEntity.ok()
                        .body(findBlockAppointmentSeriesUseCase.find(clinicId, seriesId));
            case "full":
                return ResponseEntity.ok()
                        .body(findFullAppointmentSeriesUseCase.find(clinicId, seriesId));
            default:
                throw new IllegalArgumentException("Appointment Structure is not supported");
        }
    }
}
