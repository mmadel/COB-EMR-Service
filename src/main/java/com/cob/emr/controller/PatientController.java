package com.cob.emr.controller;

import com.cob.emr.model.patient.PatientModel;
import com.cob.emr.response.ResponseHandler;
import com.cob.emr.service.patient.creator.PatientCreatorService;
import com.cob.emr.service.patient.updater.PatientUpdaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/patient")
public class PatientController {

    @Autowired
    PatientCreatorService creatorService;

    @Autowired
    PatientUpdaterService updaterService;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody PatientModel model) {
        return ResponseHandler
                .generateResponse("Successfully added Patient!",
                        HttpStatus.OK,
                        creatorService.create(model));
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody PatientModel model) {
        return ResponseHandler
                .generateResponse("Successfully updated Patient!",
                        HttpStatus.OK,
                        updaterService.update(model));
    }
}
