package com.cob.emr.controller.calendar.tmp;

import com.cob.emr.model.calendar.CalendarModel;
import com.cob.emr.usecases.calendar.tmp.CreateCalendarUseCaseq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/calendarq")
public class CalendarControllerq {
    @Autowired
    CreateCalendarUseCaseq createCalendarUseCaseq;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody CalendarModel model) {
        return ResponseEntity.ok()
                .body(createCalendarUseCaseq.create(model));
    }
}
