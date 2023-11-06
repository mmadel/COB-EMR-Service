package com.cob.emr.controller;

import com.cob.emr.exception.business.ClinicException;
import com.cob.emr.model.clinic.ClinicModel;
import com.cob.emr.model.patient.PatientModel;
import com.cob.emr.response.ResponseHandler;
import com.cob.emr.usecases.clinic.*;
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
    @Autowired
    FindClinicsForUserUseCase findClinicsForUserUseCase;
    @Autowired
    FindClinicByOrganizationIdUseCase findClinicByOrganizationIdUseCase;

    @Autowired
    DeleteClinicUseCase deleteClinicUseCase;

    @Autowired
    FindClinicUseCase findClinicUseCase;

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

    @GetMapping(path = "/find/clinics/userUUID/{userUUID}")
    @ResponseBody
    public ResponseEntity getByUserUUID(@PathVariable String userUUID) {
        return new ResponseEntity(findClinicsForUserUseCase.find(userUUID), HttpStatus.OK);
    }

    @GetMapping(path = "/find/organization/{organizationId}")
    @ResponseBody
    public ResponseEntity<Object> findAllByOrganizationID(@PathVariable Long organizationId) {
        return ResponseHandler
                .generateResponse("Successfully Find All Clinics By Organization Id",
                        HttpStatus.OK,
                        findClinicByOrganizationIdUseCase.find(organizationId));
    }

    @DeleteMapping(path = "/delete/clinic/{clinicId}")
    @ResponseBody
    public void deleteClinic(@PathVariable Long clinicId) throws ClinicException {
        deleteClinicUseCase.delete(clinicId);
    }
    @GetMapping(path = "/find/clinic/{clinicId}")
    @ResponseBody
    public ResponseEntity findClinicById(@PathVariable Long clinicId){
        return new ResponseEntity(findClinicUseCase.find(clinicId), HttpStatus.OK);
    }
}
