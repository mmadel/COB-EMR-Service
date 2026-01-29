package com.cob.emr.controller.calendar;

import com.cob.emr.exception.business.CalendarException;
import com.cob.emr.model.calendar.CalendarModel;
import com.cob.emr.model.calendar.CalendarsUpdateModel;
import com.cob.emr.usecases.calendar.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
    UnAttachCalendarWithClinicUseCase unattachCalendarWithClinicUseCase;
    @Autowired
    GetAttachedCalendarsUseCase getAttachedCalendarsUseCase;
    @Autowired
    UpdateCalendarsUseCase updateCalendarsUseCase;
    @Autowired
    UpdateCalendarNameUSeCase updateCalendarNameUSeCase;
    @Autowired
    DeleteCalendarUseCase deleteCalendarUseCase;

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
                .body(retrieveCalendarsUseCase.retrieve(paging, uuid, clinicId));
    }

    @PutMapping("/attach/calendarIds/{calendarIds}/clinic/{clinicId}/uuid/{uuid}")
    public ResponseEntity attachCalendar(@PathVariable("calendarIds") Long[] calendarIds, @PathVariable("clinicId") Long clinicId, @PathVariable("uuid") String uuid) {
        attachCalendarWithClinicUseCase.attach(Arrays.asList(calendarIds), clinicId, uuid);
        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/unattach/calendarIds/{calendarIds}/clinic/{clinicId}/uuid/{uuid}")
    public ResponseEntity unattachCalendar(@PathVariable("calendarIds") Long[] calendarIds, @PathVariable("clinicId") Long clinicId, @PathVariable("uuid") String uuid) {
        unattachCalendarWithClinicUseCase.unattached(Arrays.asList(calendarIds), clinicId, uuid);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/get-attached/clinicId/{clinicId}/uuid/{uuid}")
    public ResponseEntity getAttachedCalendar(@PathVariable("clinicId") Long clinicId, @PathVariable("uuid") String uuid) {
        return ResponseEntity.ok()
                .body(getAttachedCalendarsUseCase.get(clinicId, uuid));
    }

    @PutMapping("/update")
    public ResponseEntity updateCalendars(@RequestBody CalendarsUpdateModel calendarsUpdateModel) {
        updateCalendarsUseCase.update(calendarsUpdateModel);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/update/calendar")
    public ResponseEntity updateCalendar(@RequestBody CalendarModel model) {
        updateCalendarNameUSeCase.update(model);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{calendarId}")
    public ResponseEntity delete(@PathVariable Long calendarId) throws CalendarException {
        deleteCalendarUseCase.delete(calendarId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
