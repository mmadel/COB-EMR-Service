package com.cob.emr.controller;

import com.cob.emr.model.appointment.AppointmentTypeModel;
import com.cob.emr.response.ResponseHandler;
import com.cob.emr.service.appointment.AppointmentCreatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/appointment/type")
public class AppointmentTypeController {

    @Autowired
    AppointmentCreatorService creatorService;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody AppointmentTypeModel model) {
        return ResponseHandler
                .generateResponse("Successfully added AppointmentType",
                        HttpStatus.OK,
                        creatorService.createOrUpdate(model));
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody AppointmentTypeModel model) {
        return ResponseHandler
                .generateResponse("Successfully updated AppointmentType",
                        HttpStatus.OK,
                        creatorService.createOrUpdate(model));
    }

    @GetMapping("/find/clinic/{clinicId}")
    public ResponseEntity<Object> findAll(@PathVariable("clinicId") Long clinicId) {
        return ResponseHandler
                .generateResponse("Successfully Find all AppointmentTypes",
                        HttpStatus.OK,
                        creatorService.findAll(clinicId));
    }

    @GetMapping("/find/clinic/{clinicId}/id/{id}")
    public ResponseEntity<Object> findById(@PathVariable("clinicId") Long clinicId
            , @PathVariable("id") Long id) {
        return ResponseHandler
                .generateResponse("Successfully Find all AppointmentTypes",
                        HttpStatus.OK,
                        creatorService.findById(id, clinicId));
    }
}
