package com.cob.emr.controller.patient;

import com.cob.emr.exception.business.PatientCaseException;
import com.cob.emr.model.patient.PatientModel;
import com.cob.emr.model.patient.chart.PatientChartAccessibilityModelRequest;
import com.cob.emr.response.ResponseHandler;
import com.cob.emr.service.patient.creator.PatientCreatorService;
import com.cob.emr.service.patient.finder.PatientFinderService;
import com.cob.emr.service.patient.updater.PatientUpdaterService;
import com.cob.emr.usecases.patient.CreatePatientUseCase;
import com.cob.emr.usecases.patient.DeletePatientUseCase;
import com.cob.emr.usecases.patient.FindPatientByClinicIdentificationUseCase;
import com.cob.emr.usecases.patient.FindPatientByNameUseCase;
import com.cob.emr.usecases.patient.chart.PatientChartAccessibilityUseCase;
import com.cob.emr.usecases.patient.update.UpdatePatientUseCase;
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

    @Autowired
    CreatePatientUseCase createPatientUseCase;
    @Autowired
    UpdatePatientUseCase updatePatientUseCase;
    @Autowired
    FindPatientByClinicIdentificationUseCase findPatientByClinicIdentification;
    @Autowired
    DeletePatientUseCase deletePatientUseCase;
    @Autowired
    PatientChartAccessibilityUseCase patientChartAccessibilityUseCase;

    @Autowired
    FindPatientByNameUseCase findPatientByNameUseCase;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody PatientModel model) {
        return ResponseHandler
                .generateResponse("Successfully added Patient!",
                        HttpStatus.OK,
                        createPatientUseCase.create(model));
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody PatientModel model) throws PatientCaseException {
        updatePatientUseCase.update(model);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/patientId/{patientId}")
    public ResponseEntity delete(@PathVariable("patientId") Long patientId) {
        deletePatientUseCase.delete(patientId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/find/clinicId/{clinicId}")
    public ResponseEntity<Object> findAll(@RequestParam(name = "offset") String offset,
                                          @RequestParam(name = "limit") String limit,
                                          @PathVariable("clinicId") Long clinicId) {
        Pageable paging = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit));
        return ResponseHandler
                .generateResponse("Successfully Find All Patients!",
                        HttpStatus.OK, null,
                        findPatientByClinicIdentification.find(paging, clinicId));
    }

    @GetMapping("/find/all/clinicId/{clinicId}")
    public ResponseEntity findAll(@PathVariable("clinicId") Long clinicId) {
        return new ResponseEntity(finderService.finsAll(clinicId), HttpStatus.OK);
    }

    @GetMapping("/find/clinicId/{clinicId}/patient/{patientId}")
    public ResponseEntity<Object> findById(@PathVariable("clinicId") Long clinicId,
                                           @PathVariable("patientId") Long patientId) {
        return ResponseHandler
                .generateResponse("Successfully Find Patient!",
                        HttpStatus.OK,
                        finderService.findById(clinicId, patientId));
    }


    @PostMapping("/check/chart/accessibility")
    public ResponseEntity checkPatientChartAccessibility(@RequestBody PatientChartAccessibilityModelRequest patientChartAccessibilityModelRequest) {
        return ResponseEntity.ok()
                .body(patientChartAccessibilityUseCase.check(patientChartAccessibilityModelRequest));
    }

    @GetMapping("/find/name/{name}/clinic-id/{clinicId}")
    public ResponseEntity findPatientByName(@PathVariable("name") String name ,@PathVariable("clinicId") Long clinicId) {
        return ResponseEntity.ok()
                .body(findPatientByNameUseCase.find(name,clinicId));
    }
}
