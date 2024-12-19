package com.cob.emr.controller.calendar;

import com.cob.emr.model.calendar.CalendarModel;
import com.cob.emr.usecases.calendar.AttachCalendarWithClinicUseCase;
import com.cob.emr.usecases.calendar.CreateCalendarUseCase;
import com.cob.emr.usecases.calendar.RetrieveCalendarsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping(value = "/calendar")
public class CalendarController {
    @Autowired
    CreateCalendarUseCase createCalendarUseCase;
    @Autowired
    RetrieveCalendarsUseCase retrieveCalendarsUseCase;
    @Autowired
    AttachCalendarWithClinicUseCase attachCalendarWithClinicUseCase;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody CalendarModel model) {
        return ResponseEntity.ok()
                .body(createCalendarUseCase.create(model));
    }

    @GetMapping("/get/uuid/{uuid}")
    public ResponseEntity get(@PathVariable("uuid") String uuid,@RequestParam(name = "offset") String offset,
                              @RequestParam(name = "limit") String limit) {
        Pageable paging = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit));
        return ResponseEntity.ok()
                .body(retrieveCalendarsUseCase.retrieve(paging, uuid));
    }

    @PutMapping("/attach/calendarIds/{calendarIds}/clinic/{clinicId}")
    public ResponseEntity attachCalendar(@PathVariable("calendarIds") Long[] calendarIds, @PathVariable("clinicId") Long clinicId) {
        attachCalendarWithClinicUseCase.attach(Arrays.asList(calendarIds), clinicId);
        return ResponseEntity.ok()
                .build();
    }
}
