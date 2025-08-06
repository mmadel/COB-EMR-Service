package com.cob.emr.usecases.calendar.tmp;

import com.cob.emr.model.calendar.CalendarUpdateAttributeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChangeCalendarsAttributesUseCase {
    @Autowired
    ChangeCalendarsAttachmentUseCase changeCalendarsAttachmentUseCase;
    @Autowired
    ChangeCalendarsAccessibilityUseCase changeCalendarsAccessibilityUseCase;

    public void change(CalendarUpdateAttributeModel model) {
        if (model.getCalendarAttachmentAttributes().size() > 0)
            changeCalendarsAttachmentUseCase.change(model);
        if (model.getCalendarAccessibilityAttributes().size() > 0)
            changeCalendarsAccessibilityUseCase.change(model);
    }
}
