package com.cob.emr.controller;

import com.cob.emr.model.patient.PatientModel;
import com.cob.emr.response.ResponseHandler;
import com.cob.emr.service.patient.creator.PatientCreatorService;
import com.cob.emr.service.patient.finder.PatientFinderService;
import com.cob.emr.service.patient.updater.PatientUpdaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    PatientFinderService finderService;

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

    @GetMapping("/find/clinicId/{clinicId}")
    public ResponseEntity<Object> findAll(@RequestParam(name = "offset") String offset,
                                          @RequestParam(name = "limit") String limit,
                                          @PathVariable("clinicId") Long clinicId) {
        Pageable paging = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit));
        return ResponseHandler
                .generateResponse("Successfully Find All Patients!",
                        HttpStatus.OK,
                        finderService.findAll(paging, clinicId));
    }

    @GetMapping("/find/clinicId/{clinicId}/patient/{patientId}")
    public ResponseEntity<Object> findById(@PathVariable("clinicId") Long clinicId,
                                           @PathVariable("patientId") Long patientId) {
        return ResponseHandler
                .generateResponse("Successfully Find Patient!",
                        HttpStatus.OK,
                        finderService.findById(clinicId, patientId));
    }
}
