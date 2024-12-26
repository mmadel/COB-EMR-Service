package com.cob.emr.controller.calendar;

import com.cob.emr.model.calendar.CalendarModel;
import com.cob.emr.usecases.calendar.AttachCalendarWithClinicUseCase;
import com.cob.emr.usecases.calendar.CreateCalendarUseCase;
import com.cob.emr.usecases.calendar.GetAttachedCalendarsUseCase;
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
    @Autowired
    GetAttachedCalendarsUseCase getAttachedCalendarsUseCase;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody CalendarModel model) {
        return ResponseEntity.ok()
                .body(createCalendarUseCase.create(model));
    }

    @GetMapping("/get/uuid/{uuid}/clinicId/{clinicId}")
    public ResponseEntity get(@PathVariable("uuid") String uuid, @RequestParam(name = "offset") String offset,
                              @RequestParam(name = "limit") String limit, @PathVariable("clinicId") Long clinicId) {
        Pageable paging = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit));
        return ResponseEntity.ok()
                .body(retrieveCalendarsUseCase.retrieve(paging, uuid,clinicId));
    }

    @PutMapping("/attach/calendarIds/{calendarIds}/clinic/{clinicId}/uuid/{uuid}")
    public ResponseEntity attachCalendar(@PathVariable("calendarIds") Long[] calendarIds, @PathVariable("clinicId") Long clinicId, @PathVariable("uuid") String uuid) {
        attachCalendarWithClinicUseCase.attach(Arrays.asList(calendarIds), clinicId, uuid);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/get-attached/clinicId/{clinicId}/uuid/{uuid}")
    public ResponseEntity getAttachedCalendar(@PathVariable("clinicId") Long clinicId, @PathVariable("uuid") String uuid) {
        return ResponseEntity.ok()
                .body(getAttachedCalendarsUseCase.get(clinicId, uuid));
    }
}
