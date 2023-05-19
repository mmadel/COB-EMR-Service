package com.cob.emr.controller;

import com.cob.emr.response.ResponseHandler;
import com.cob.emr.service.appointment.chart.AppointmentChartPreviousFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment/chart/previous")
public class AppointmentPreviousChartController {

    @Autowired
    AppointmentChartPreviousFinderService finderService;

    @GetMapping(path = "/find/patientId/{patientId}/clinicId/{clinicId}")
    public ResponseEntity<Object> findAllPreviousAppointments(@PathVariable Long patientId,
                                                              @PathVariable Long clinicId,
                                                              @RequestParam(name = "offset") String offset,
                                                              @RequestParam(name = "limit") String limit) {
        Pageable page = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit),Sort.by("startDate").descending());
        return ResponseHandler
                .generateResponse("Successfully find All Incoming Appointments",
                        HttpStatus.OK,null,
                        finderService.findAllPreviousAppointments(patientId, clinicId, page));
    }

    @GetMapping(path = "/find/patientId/{patientId}/clinicId/{clinicId}/patientCaseId/{patientCaseId}/page/{page}/size/{size}")
    public ResponseEntity<Object> findPreviousAppointmentsByCase(@PathVariable Long patientId, @PathVariable Long clinicId,
                                                                 @PathVariable Long patientCaseId,
                                                                 @RequestParam(name = "offset") String offset,
                                                                 @RequestParam(name = "limit") String limit) {
        Pageable page = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit),Sort.by("startDate").descending());
        return ResponseHandler
                .generateResponse("Successfully find Previous Appointments By Case",
                        HttpStatus.OK,
                        finderService.findPreviousAppointmentsByCase(patientId, clinicId, patientCaseId, page));
    }
}
