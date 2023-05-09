package com.cob.emr.controller;

import com.cob.emr.model.appointment.AppointmentModel;
import com.cob.emr.response.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody AppointmentModel model) {
        return ResponseHandler
                .generateResponse("Successfully added Appointment",
                        HttpStatus.OK,
                        null);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> update(@RequestBody AppointmentModel model) {
        return ResponseHandler
                .generateResponse("Successfully added Appointment",
                        HttpStatus.OK,
                        null);
    }
}
