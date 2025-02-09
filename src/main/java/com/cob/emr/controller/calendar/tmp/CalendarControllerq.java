package com.cob.emr.controller.calendar.tmp;

import com.cob.emr.model.calendar.CalendarModel;
import com.cob.emr.model.calendar.CalendarUpdateAttributeModel;
import com.cob.emr.usecases.calendar.GetAttachedCalendarsUseCase;
import com.cob.emr.usecases.calendar.tmp.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/calendarq")
public class CalendarControllerq {
    @Autowired
    CreateCalendarUseCaseq createCalendarUseCaseq;
    @Autowired
    ChangeCalendarsAttachmentUseCase changeCalendarsAttachmentUseCase;
    @Autowired
    ChangeCalendarsAccessibilityUseCase changeCalendarsAccessibilityUseCase;
    @Autowired
    ChangeCalendarsAttributesUseCase changeCalendarsAttributesUseCase;
    @Autowired
    GetAttachedCalendarsUseCase getAttachedCalendarsUseCase;
    @Autowired
    ListCalendarsUseCase listCalendarsUseCase;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody CalendarModel model) {
        return ResponseEntity.ok()
                .body(createCalendarUseCaseq.create(model));
    }

    @PutMapping("/change/attachment")
    public ResponseEntity changeCalendarsAttachment(@RequestBody CalendarUpdateAttributeModel model) {
        changeCalendarsAttachmentUseCase.change(model);
        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/change/accessibility")
    public ResponseEntity changeCalendarsAccessibility(@RequestBody CalendarUpdateAttributeModel model) {
        changeCalendarsAccessibilityUseCase.change(model);
        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/change/attributes")
    public ResponseEntity changeCalendarsAttributes(@RequestBody CalendarUpdateAttributeModel model) {
        changeCalendarsAttributesUseCase.change(model);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/list/clinic-id/{clinicId}/user/{user}")
    public ResponseEntity listCalendars(@PathVariable("clinicId") Long clinicId
            , @PathVariable("user") String user
            , @RequestParam(name = "offset") int offset
            , @RequestParam(name = "limit") int limit) {
        return ResponseEntity.ok()
                .body(listCalendarsUseCase.list(offset + 1, limit, clinicId, user));
    }

    @GetMapping("/get-attached/clinicId/{clinicId}/uuid/{uuid}")
    public ResponseEntity getAttachedCalendar(@PathVariable("clinicId") Long clinicId, @PathVariable("uuid") String uuid) {
        return ResponseEntity.ok()
                .body(getAttachedCalendarsUseCase.get(clinicId, uuid));
    }
}
