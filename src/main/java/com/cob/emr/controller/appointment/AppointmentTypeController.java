package com.cob.emr.controller.appointment;

import com.cob.emr.model.appointment.AppointmentTypeModel;
import com.cob.emr.response.ResponseHandler;
import com.cob.emr.service.appointment.type.AppointmentTypeFinderService;
import com.cob.emr.usecases.appointment.type.CreateAppointmentTypeUseCase;
import com.cob.emr.usecases.appointment.type.FindAppointmentTypeUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/appointment/type")
public class AppointmentTypeController {

    @Autowired
    AppointmentTypeFinderService finderService;
    @Autowired
    CreateAppointmentTypeUseCase createAppointmentTypeUseCase;

    @Autowired
    FindAppointmentTypeUseCase findAppointmentTypeUseCase;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody AppointmentTypeModel model) {
        return ResponseHandler
                .generateResponse("Successfully added AppointmentType",
                        HttpStatus.OK,
                        createAppointmentTypeUseCase.create(model));
    }

    @GetMapping("/find")
    public ResponseEntity<Object> findAll() {
        return ResponseHandler
                .generateResponse("Successfully Find all AppointmentTypes",
                        HttpStatus.OK,
                        findAppointmentTypeUseCase.findAll());
    }
}
