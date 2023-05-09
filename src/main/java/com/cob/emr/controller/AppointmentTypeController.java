package com.cob.emr.controller;

import com.cob.emr.model.appointment.AppointmentTypeModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/appointment/type")
public class AppointmentTypeController {

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody AppointmentTypeModel model) {
        return null;
    }

    @PostMapping("/update")
    public ResponseEntity<Object> update(@RequestBody AppointmentTypeModel model) {
        return null;
    }

    @GetMapping("/find/clinic/{clinicId}")
    public ResponseEntity<Object> findAll(@PathVariable("clinicId") Long clinicId) {
        return null;
    }

    @GetMapping("/find/clinic/{clinicId}/id/{id}")
    public ResponseEntity<Object> findById(@PathVariable("clinicId") Long clinicId
            , @PathVariable("id") Long id) {
        return null;
    }
}
