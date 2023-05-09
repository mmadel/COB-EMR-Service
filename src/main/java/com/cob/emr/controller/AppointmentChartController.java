package com.cob.emr.controller;

import com.cob.emr.response.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointment/chart")
public class AppointmentChartController {
    @GetMapping(path = "/find/incoming/patientId/{patientId}/clinicId/{clinicId}/page/{page}/size/{size}")
    public ResponseEntity<Object> findAllIncomingAppointments(@PathVariable Long patientId,
                                                              @PathVariable Long clinicId,
                                                              @PathVariable() int page,
                                                              @PathVariable() int size) {
        return ResponseHandler
                .generateResponse("Successfully find All Incoming Appointments",
                        HttpStatus.OK,
                        null);

    }

    @GetMapping(path = "/find/previous/patientId/{patientId}/clinicId/{clinicId}/patientCaseId/{patientCaseId}/page/{page}/size/{size}")
    public ResponseEntity<Object> findIncomingAppointmentsByCase(@PathVariable Long patientId, @PathVariable Long clinicId,
                                                                 @PathVariable Long patientCaseId,
                                                                 @PathVariable() int page,
                                                                 @PathVariable() int size) {
        return ResponseHandler
                .generateResponse("Successfully find All Incoming Appointments By Case  ",
                        HttpStatus.OK,
                        null);

    }

    @GetMapping(path = "/find/previous/patientId/{patientId}/clinicId/{clinicId}/page/{page}/size/{size}")
    public ResponseEntity<Object> findAllPreviousAppointments(@PathVariable Long patientId,
                                                              @PathVariable Long clinicId,
                                                              @PathVariable() int page,
                                                              @PathVariable() int size) {
        return ResponseHandler
                .generateResponse("Successfully find All Previous Appointments",
                        HttpStatus.OK,
                        null);
    }

    @GetMapping(path = "/find/previous/patientId/{patientId}/clinicId/{clinicId}/patientCaseId/{patientCaseId}/page/{page}/size/{size}")
    public ResponseEntity<Object> findPreviousAppointmentsByCase(@PathVariable Long patientId, @PathVariable Long clinicId,
                                                                 @PathVariable Long patientCaseId,
                                                                 @PathVariable() int page,
                                                                 @PathVariable() int size) {
        return ResponseHandler
                .generateResponse("Successfully find Previous Appointments By Case",
                        HttpStatus.OK,
                        null);
    }

    @GetMapping(path = "/find/cancel/patientId/{patientId}/clinicId/{clinicId}/patientCaseId/{patientCaseId}/page/{page}/size/{size}")
    public ResponseEntity<Object> findCancelAppointments(@PathVariable Long patientId,
                                                         @PathVariable Long clinicId,
                                                         @PathVariable Long patientCaseId,
                                                         @PathVariable() int page,
                                                         @PathVariable() int size) {
        return ResponseHandler
                .generateResponse("Successfully find Cancel Appointments",
                        HttpStatus.OK,
                        null);
    }

    @GetMapping(path = "/find/noshow/patientId/{patientId}/clinicId/{clinicId}/patientCaseId/{patientCaseId}/page/{page}/size/{size}")
    public ResponseEntity<Object> findNoShowAppointments(@PathVariable Long patientId, @PathVariable Long clinicId,
                                                         @PathVariable Long patientCaseId,
                                                         @PathVariable() int page,
                                                         @PathVariable() int size) {
        return ResponseHandler
                .generateResponse("Successfully find No-Show Appointments",
                        HttpStatus.OK,
                        null);
    }

    @GetMapping(path = "/find/cancel/noshow/patientId/{patientId}/clinicId/{clinicId}/patientCaseId/{patientCaseId}/page/{page}/size/{size}")
    public ResponseEntity<Object> findCancelNoShowAppointments(@PathVariable Long patientId, @PathVariable Long clinicId,
                                                               @PathVariable Long patientCaseId,
                                                               @PathVariable() int page,
                                                               @PathVariable() int size) {
        return ResponseHandler
                .generateResponse("Successfully find Cancel No-Show Appointments",
                        HttpStatus.OK,
                        null);
    }

}
