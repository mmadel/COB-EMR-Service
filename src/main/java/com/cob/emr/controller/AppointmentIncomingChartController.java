package com.cob.emr.controller;

import com.cob.emr.response.ResponseHandler;
import com.cob.emr.service.appointment.chart.AppointmentChartIncomingFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointment/chart/incoming")
public class AppointmentIncomingChartController {

    @Autowired
    AppointmentChartIncomingFinderService finderService;

    @GetMapping(path = "/find/patientId/{patientId}/clinicId/{clinicId}/page/{page}/size/{size}")
    public ResponseEntity<Object> findAllIncomingAppointments(@PathVariable Long patientId,
                                                              @PathVariable Long clinicId,
                                                              @PathVariable() int page,
                                                              @PathVariable() int size) {
        Pageable paging = PageRequest.of(page, size, Sort.by("startDate").descending());
        return ResponseHandler
                .generateResponse("Successfully find All Incoming Appointments",
                        HttpStatus.OK,
                        finderService.findAllIncomingAppointments(patientId, clinicId, paging));

    }

    @GetMapping(path = "/find/patientId/{patientId}/clinicId/{clinicId}/patientCaseId/{patientCaseId}/page/{page}/size/{size}")
    public ResponseEntity<Object> findIncomingAppointmentsByCase(@PathVariable Long patientId, @PathVariable Long clinicId,
                                                                 @PathVariable Long patientCaseId,
                                                                 @PathVariable() int page,
                                                                 @PathVariable() int size) {
        Pageable paging = PageRequest.of(page, size, Sort.by("startDate").descending());
        return ResponseHandler
                .generateResponse("Successfully find All Incoming Appointments By Case  ",
                        HttpStatus.OK,
                        finderService.findIncomingAppointmentsByCase(patientId, clinicId, patientCaseId, paging));

    }
}
