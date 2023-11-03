package com.cob.emr.controller;

import com.cob.emr.model.clinic.ClinicModel;
import com.cob.emr.model.patient.PatientModel;
import com.cob.emr.response.ResponseHandler;
import com.cob.emr.usecases.clinic.CreateClinicUseCase;
import com.cob.emr.usecases.clinic.FindClinicsByListOfIdentificationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/clinic")
public class ClinicController {


    @Autowired
    CreateClinicUseCase createClinicUseCase;
    @Autowired
    FindClinicsByListOfIdentificationUseCase findClinicsByListOfIdentification;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody ClinicModel clinicModel) {
        return ResponseHandler
                .generateResponse("Successfully added clinic!",
                        HttpStatus.OK,
                        createClinicUseCase.create(clinicModel));
    }
    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody ClinicModel model) {
        return null;
    }

    @GetMapping("/find/ids/{ids}")
    public ResponseEntity<Object> findByIds(@PathVariable("ids") List<Long> ids) {
        return ResponseHandler
                .generateResponse("Successfully Find All Clinics By Ids",
                        HttpStatus.OK,
                        findClinicsByListOfIdentification.find(ids));
    }
}
