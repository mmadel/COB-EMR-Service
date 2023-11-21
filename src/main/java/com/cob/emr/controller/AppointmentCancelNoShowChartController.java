package com.cob.emr.controller;

import com.cob.emr.response.ResponseHandler;
import com.cob.emr.service.appointment.chart.AppointmentChartCancelNoShowFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment/chart/cno")
public class AppointmentCancelNoShowChartController {
    @Autowired
    AppointmentChartCancelNoShowFinderService finderService;

    @GetMapping(path = "/find/cancel/patientId/{patientId}/clinicId/{clinicId}/patientCaseId/{patientCaseId}/page/{page}/size/{size}")
    public ResponseEntity<Object> findCancelAppointments(@PathVariable Long patientId,
                                                         @PathVariable Long clinicId,
                                                         @PathVariable Long patientCaseId,
                                                         @PathVariable() int page,
                                                         @PathVariable() int size) {
        Pageable paging = PageRequest.of(page, size, Sort.by("startDate").descending());
        return ResponseHandler
                .generateResponse("Successfully find Cancel Appointments",
                        HttpStatus.OK,
                        finderService.findCancelAppointments(patientId, clinicId, clinicId, paging));
    }

    @GetMapping(path = "/find/noshow/patientId/{patientId}/clinicId/{clinicId}/patientCaseId/{patientCaseId}/page/{page}/size/{size}")
    public ResponseEntity<Object> findNoShowAppointments(@PathVariable Long patientId, @PathVariable Long clinicId,
                                                         @PathVariable Long patientCaseId,
                                                         @PathVariable() int page,
                                                         @PathVariable() int size) {
        Pageable paging = PageRequest.of(page, size, Sort.by("startDate").descending());
        return ResponseHandler
                .generateResponse("Successfully find No-Show Appointments",
                        HttpStatus.OK,
                        finderService.findNoShowAppointments(patientId, clinicId, clinicId, paging));
    }

    @GetMapping(path = "/find/cancel/noshow/patientId/{patientId}/clinicId/{clinicId}/patientCaseId/{patientCaseId}")
    public ResponseEntity<Object> findCancelNoShowAppointments(@PathVariable Long patientId, @PathVariable Long clinicId,
                                                               @PathVariable Long patientCaseId,
                                                               @RequestParam(name = "offset") String offset,
                                                               @RequestParam(name = "limit") String limit) {
        Pageable page = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit),Sort.by("startDate").descending());
        return ResponseHandler
                .generateResponse("Successfully find Cancel No-Show Appointments",
                        HttpStatus.OK,null,
                        finderService.findCancelNoShowAppointments(patientId, clinicId, patientCaseId, page));
    }
}
