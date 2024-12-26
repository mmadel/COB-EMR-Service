package com.cob.emr.controller.scheduler;

import com.cob.emr.model.scheduler.SchedulerConfiguration;
import com.cob.emr.model.scheduler.SchedulerSettings;
import com.cob.emr.usecases.scheduler.*;
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
    @Autowired
    FindAllSchedulersConfigurationUseCase findAllSchedulersConfigurationUseCase;
    @Autowired
    CreateSchedulerSettingsUseCase createSchedulerSettingsUseCase;
    @Autowired
    FindSchedulerSettingsForClinicUseCase findSchedulerSettingsForClinicUseCase;

    @PostMapping("/create/configuration")
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

    @GetMapping("/find/organizationId/{organizationId}")
    public ResponseEntity findAll(@PathVariable Long organizationId) {
        return new ResponseEntity(findAllSchedulersConfigurationUseCase
                .find(organizationId)
                , HttpStatus.OK);
    }

    @PostMapping("/create/settings")
    public ResponseEntity create(@RequestBody SchedulerSettings model) {
        return ResponseEntity.ok()
                .body(createSchedulerSettingsUseCase.create(model));
    }

    @GetMapping("/find/settings/clinicId/{clinicId}")
    public ResponseEntity find(@PathVariable("clinicId") Long clinicId) {
        return ResponseEntity.ok()
                .body(findSchedulerSettingsForClinicUseCase.find(clinicId));
    }
}
