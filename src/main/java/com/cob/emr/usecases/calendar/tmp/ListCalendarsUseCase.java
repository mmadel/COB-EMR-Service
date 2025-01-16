package com.cob.emr.usecases.calendar.tmp;

import com.cob.emr.entity.calendar.CalendarEntity;
import com.cob.emr.entity.calendar.UserCalendarAttachment;
import com.cob.emr.model.calendar.CalendarModel;
import com.cob.emr.model.calendar.CalendarsListModel;
import com.cob.emr.model.response.CalendarResponse;
import com.cob.emr.repositories.calendar.CalendarRepository;
import com.cob.emr.repositories.calendar.UserCalendarAttachmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ListCalendarsUseCase {
    @Autowired
    CalendarRepository calendarRepository;
    @Autowired
    UserCalendarAttachmentRepository userCalendarAttachmentRepository;
    @Autowired
    ModelMapper mapper;

    public CalendarResponse list(Pageable paging, Long clinicId, String userUUID) {
        Page<CalendarEntity> pages = calendarRepository.listCalendars(paging, clinicId);
        long total = (pages).getTotalElements();
        List<CalendarsListModel> records = pages.stream()
                .filter(calendarEntity -> {
                    if (calendarEntity.getCreatedBy().equals(userUUID)) {
                        return true;
                    } else {
                        if (calendarEntity.getIsPublic())
                            return true;
                        else
                            return false;
                    }
                })
                .map(calendarEntity -> {
                    Optional<UserCalendarAttachment> optional = userCalendarAttachmentRepository.findByCalendar_IdAndUser(calendarEntity.getId(), userUUID);
                    if (optional.isPresent())
                        return fillCalendarsListModel(calendarEntity, optional.get());
                    else
                        return fillCalendarsListModel(calendarEntity, null);
                }).collect(Collectors.toList());
        return CalendarResponse.builder()
                .number_of_records(records.size())
                .number_of_matching_records((int) total)
                .records(records)
                .build();
    }

    private CalendarsListModel fillCalendarsListModel(CalendarEntity calendarEntity, UserCalendarAttachment userCalendarAttachment) {
        return CalendarsListModel.builder()
                .isAttach(userCalendarAttachment == null ? false : userCalendarAttachment.getIsAttached())
                .userCalendarAttachmentId(userCalendarAttachment == null ? null : userCalendarAttachment.getId())
                .name(calendarEntity.getName())
                .createdBy(calendarEntity.getCreatedBy())
                .calendarId(calendarEntity.getId())
                .clinicId(calendarEntity.getClinic().getId())
                .isPublic(calendarEntity.getIsPublic())
                .build();
    }
}
