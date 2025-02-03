package com.cob.emr.controller.appointment;

import com.cob.emr.model.appointment.SchedulerAppointment;
import com.cob.emr.model.appointment.series.AppointmentFullSeries;
import com.cob.emr.usecases.appointment.create.CreateSchedulerAppointment;
import com.cob.emr.usecases.appointment.series.EditFullAppointmentSeriesUseCase;
import com.cob.emr.usecases.appointment.update.UpdateSeriesSchedulerAppointmentUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment/series")
public class AppointmentSeriesController {

    @Autowired
    EditFullAppointmentSeriesUseCase editFullAppointmentSeriesUseCase;
    @Autowired
    UpdateSeriesSchedulerAppointmentUseCase updateSeriesSchedulerAppointmentUseCase;
    @Autowired
    @Qualifier("CreateSeriesSchedulerAppointment")
    CreateSchedulerAppointment createSchedulerAppointment;

    @PostMapping("/create")
    public ResponseEntity createRepetition(@RequestBody SchedulerAppointment model) {
        return ResponseEntity.ok()
                .body(createSchedulerAppointment.create(model));
    }

    @PostMapping("/update")
    public ResponseEntity updateRepetition(@RequestBody SchedulerAppointment model) {
        return ResponseEntity.ok()
                .body(updateSeriesSchedulerAppointmentUseCase.update(model));
    }


    @PutMapping("/series/seriesId/{seriesId}")
    public ResponseEntity editAppointmentSeries(@RequestBody AppointmentFullSeries model, @PathVariable Integer seriesId) {
        return ResponseEntity.ok()
                .body(editFullAppointmentSeriesUseCase.edit(model, seriesId));
    }
}
