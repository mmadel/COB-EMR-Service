package com.cob.emr.controller.appointment;

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

    @GetMapping(path = "/find/patientId/{patientId}")
    public ResponseEntity<Object> findAllPreviousAppointments(@PathVariable Long patientId,
                                                              @RequestParam(name = "offset") String offset,
                                                              @RequestParam(name = "limit") String limit) {
        Pageable page = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit),Sort.by("startDate").descending());
        return ResponseHandler
                .generateResponse("Successfully find All Incoming Appointments",
                        HttpStatus.OK,null,
                        finderService.findAllPreviousAppointments(patientId, page));
    }

    @GetMapping(path = "/find/patientId/{patientId}/patientCaseId/{patientCaseId}")
    public ResponseEntity<Object> findPreviousAppointmentsByCase(@PathVariable Long patientId,
                                                                 @PathVariable Long patientCaseId,
                                                                 @RequestParam(name = "offset") String offset,
                                                                 @RequestParam(name = "limit") String limit) {
        Pageable page = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit),Sort.by("startDate").descending());
        return ResponseHandler
                .generateResponse("Successfully find Previous Appointments By Case",
                        HttpStatus.OK,null,
                        finderService.findPreviousAppointmentsByCase(patientId, patientCaseId, page));
    }
}
