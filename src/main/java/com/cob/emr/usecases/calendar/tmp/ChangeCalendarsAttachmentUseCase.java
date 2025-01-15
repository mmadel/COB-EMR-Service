package com.cob.emr.usecases.calendar.tmp;

import com.cob.emr.entity.calendar.CalendarEntity;
import com.cob.emr.entity.calendar.UserCalendarAttachment;
import com.cob.emr.model.calendar.CalendarUpdateAttributeModel;
import com.cob.emr.repositories.calendar.CalendarRepository;
import com.cob.emr.repositories.calendar.UserCalendarAttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChangeCalendarsAttachmentUseCase {
    @Autowired
    CalendarRepository calendarRepository;
    @Autowired
    UserCalendarAttachmentRepository userCalendarAttachmentRepository;

    public void change(CalendarUpdateAttributeModel model) {
        model.getCalendarAttributes().forEach(calendarAttribute -> {
            UserCalendarAttachment userCalendarAttachment;
            CalendarEntity calendarEntity = calendarRepository.findById(calendarAttribute.getCalendarId())
                    .orElseThrow(() -> new IllegalArgumentException("Calendar not found during change attribute values"));
            if (calendarAttribute.getUserAttachmentId() != null)
                userCalendarAttachment = userCalendarAttachmentRepository.findById(calendarAttribute.getUserAttachmentId()).get();
            else
                userCalendarAttachment = new UserCalendarAttachment();

            userCalendarAttachment.setCalendar(calendarEntity);
            userCalendarAttachment.setUser(model.getUuid());
            userCalendarAttachment.setIsAttached(calendarAttribute.getIsAttach());
            userCalendarAttachmentRepository.save(userCalendarAttachment);
        });
    }
}
