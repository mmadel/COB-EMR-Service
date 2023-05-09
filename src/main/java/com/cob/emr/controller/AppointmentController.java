package com.cob.emr.controller;

import com.cob.emr.model.appointment.AppointmentFilterModel;
import com.cob.emr.model.appointment.AppointmentModel;
import com.cob.emr.response.ResponseHandler;
import com.cob.emr.service.appointment.AppointmentCreatorService;
import com.cob.emr.service.appointment.AppointmentFinderService;
import com.cob.emr.service.appointment.history.AppointmentHistoryFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    AppointmentCreatorService creatorService;

    @Autowired
    AppointmentFinderService finderService;

    @Autowired
    AppointmentHistoryFinderService historyFinderService;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody AppointmentModel model) {
        return ResponseHandler
                .generateResponse("Successfully added Appointment",
                        HttpStatus.OK,
                        creatorService.createOrUpdate(model));
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody AppointmentModel model) {
        return ResponseHandler
                .generateResponse("Successfully update Appointment",
                        HttpStatus.OK,
                        creatorService.createOrUpdate(model));
    }

    @GetMapping("/find/startDate/{startDate}/endDate/{endDate}/{clinicId}")
    public ResponseEntity<Object> find(@PathVariable Long startDate, @PathVariable Long endDate,
                                       @PathVariable Long clinicId) {
        return ResponseHandler
                .generateResponse("Successfully find Appointment by date range",
                        HttpStatus.OK,
                        finderService.find(startDate, endDate, clinicId));

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
}
