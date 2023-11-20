package com.cob.emr.controller;

import com.cob.emr.model.scheduler.SchedulerConfiguration;
import com.cob.emr.usecases.scheduler.CreateSchedulerConfigurationUseCase;
import com.cob.emr.usecases.scheduler.FindSchedulerConfigurationByClinicUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/scheduler")
public class SchedulerController {
    @Autowired
    CreateSchedulerConfigurationUseCase createSchedulerConfigurationUseCase;
    @Autowired
    FindSchedulerConfigurationByClinicUseCase findSchedulerConfigurationByClinicUseCase;

    @PostMapping("/configure")
    public ResponseEntity configureScheduler(@RequestBody SchedulerConfiguration schedulerConfiguration) {
        return new ResponseEntity(createSchedulerConfigurationUseCase
                .configure(schedulerConfiguration)
                , HttpStatus.OK);
    }

    @GetMapping("/find/clinicId/{clinicId}")
    public ResponseEntity get(@PathVariable("clinicId") Long clinicId) {
        return new ResponseEntity(findSchedulerConfigurationByClinicUseCase
                .find(clinicId)
                , HttpStatus.OK);
    }
}
