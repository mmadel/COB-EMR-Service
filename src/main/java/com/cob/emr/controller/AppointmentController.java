package com.cob.emr.controller;

import com.cob.emr.model.appointment.AppointmentModel;
import com.cob.emr.response.ResponseHandler;
import com.cob.emr.service.appointment.AppointmentCreatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    AppointmentCreatorService creatorService;

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
}
