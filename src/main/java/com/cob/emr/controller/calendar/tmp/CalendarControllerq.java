package com.cob.emr.controller.calendar.tmp;

import com.cob.emr.model.calendar.CalendarModel;
import com.cob.emr.model.calendar.CalendarUpdateAttributeModel;
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
            , @RequestParam(name = "offset") String offset
            , @RequestParam(name = "limit") String limit) {

        Pageable paging = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit));
        return ResponseEntity.ok()
                .body(listCalendarsUseCase.list(paging, clinicId, user));
    }
}
