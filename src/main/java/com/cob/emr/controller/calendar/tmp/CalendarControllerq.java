package com.cob.emr.controller.calendar.tmp;

import com.cob.emr.model.calendar.CalendarModel;
import com.cob.emr.model.calendar.CalendarUpdateAttributeModel;
import com.cob.emr.usecases.calendar.tmp.ChangeCalendarsAttachmentUseCase;
import com.cob.emr.usecases.calendar.tmp.CreateCalendarUseCaseq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/calendarq")
public class CalendarControllerq {
    @Autowired
    CreateCalendarUseCaseq createCalendarUseCaseq;
    @Autowired
    ChangeCalendarsAttachmentUseCase changeCalendarsAttachmentUseCase;

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
}
