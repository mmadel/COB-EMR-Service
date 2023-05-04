package com.cob.emr.controller;

import com.cob.emr.model.patient.PatientModel;
import com.cob.emr.response.ResponseHandler;
import com.cob.emr.service.patient.creator.PatientCreatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/patient")
public class PatientController {

    @Autowired
    PatientCreatorService creatorService;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody PatientModel model) {
        return ResponseHandler
                .generateResponse("Successfully added Patient!",
                        HttpStatus.OK,
                        creatorService.create(model));
    }
}
