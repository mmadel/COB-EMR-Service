package com.cob.emr.controller.calendar;

import com.cob.emr.model.calendar.CalendarModel;
import com.cob.emr.usecases.calendar.CreateCalendarUseCase;
import com.cob.emr.usecases.calendar.RetrieveCalendarsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/calendar")
public class CalendarController {
    @Autowired
    CreateCalendarUseCase createCalendarUseCase;
    @Autowired
    RetrieveCalendarsUseCase retrieveCalendarsUseCase;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody CalendarModel model) {
        return ResponseEntity.ok()
                .body(createCalendarUseCase.create(model));
    }

    @GetMapping("/get/uuid/{uuid}")
    public ResponseEntity get(@PathVariable("uuid") String uuid) {
        return ResponseEntity.ok()
                .body(retrieveCalendarsUseCase.retrieve(uuid));
    }
}
