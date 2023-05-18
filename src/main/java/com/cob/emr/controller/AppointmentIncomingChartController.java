package com.cob.emr.controller;

import com.cob.emr.response.ResponseHandler;
import com.cob.emr.service.appointment.chart.AppointmentChartIncomingFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment/chart/incoming")
public class AppointmentIncomingChartController {

    @Autowired
    AppointmentChartIncomingFinderService finderService;

    @GetMapping(path = "/find/patientId/{patientId}/clinicId/{clinicId}")
    public ResponseEntity<Object> findAllIncomingAppointments(
            @RequestParam(name = "offset") String offset,
            @RequestParam(name = "limit") String limit,
            @PathVariable Long patientId,
            @PathVariable Long clinicId) {
        Pageable page = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit),Sort.by("startDate").descending());
        return ResponseHandler
                .generateResponse("Successfully find All Incoming Appointments",
                        HttpStatus.OK,null,
                        finderService.findAllIncomingAppointments(patientId, clinicId, page));

    }

    @GetMapping(path = "/find/patientId/{patientId}/clinicId/{clinicId}/patientCaseId/{patientCaseId}")
    public ResponseEntity<Object> findIncomingAppointmentsByCase(@PathVariable Long patientId, @PathVariable Long clinicId,
                                                                 @PathVariable Long patientCaseId,
                                                                 @RequestParam(name = "offset") String offset,
                                                                 @RequestParam(name = "limit") String limit) {
        Pageable page = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit),Sort.by("startDate").descending());
        return ResponseHandler
                .generateResponse("Successfully find All Incoming Appointments By Case  ",
                        HttpStatus.OK,null,
                        finderService.findIncomingAppointmentsByCase(patientId, clinicId, patientCaseId, page));

    }
}
