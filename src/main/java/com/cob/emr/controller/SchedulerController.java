package com.cob.emr.controller;

import com.cob.emr.model.scheduler.SchedulerConfiguration;
import com.cob.emr.usecases.scheduler.CreateSchedulerConfigurationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/scheduler")
public class SchedulerController {
    @Autowired
    CreateSchedulerConfigurationUseCase createSchedulerConfigurationUseCase;

    @PostMapping("/configure")
    public ResponseEntity configureScheduler(@RequestBody SchedulerConfiguration schedulerConfiguration) {
        return new ResponseEntity(createSchedulerConfigurationUseCase
                .configure(schedulerConfiguration)
                , HttpStatus.OK);
    }
}
