package com.cob.emr.usecases.calendar;

import com.cob.emr.entity.calendar.CalendarEntity;
import com.cob.emr.model.calendar.CalendarModel;
import com.cob.emr.model.calendar.CalendarsUpdateModel;
import com.cob.emr.repositories.calendar.CalendarClinicRepository;
import com.cob.emr.repositories.calendar.CalendarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UpdateCalendarsUseCase {
    @Autowired
    CalendarClinicRepository calendarClinicRepository;
    @Autowired
    AttachCalendarWithClinicUseCase attachCalendarWithClinicUseCase;
    @Autowired
    UnAttachCalendarWithClinicUseCase unAttachCalendarWithClinicUseCase;
    @Autowired
    ChangeCalendarAccessibility ChangeCalendarAccessibility;
    @Autowired
    CalendarRepository calendarRepository;
    @Autowired
    ModelMapper mapper;

    public void update(CalendarsUpdateModel calendarsUpdateModel) {
        List<CalendarModel> list = calendarClinicRepository.findCalendars(calendarsUpdateModel.getClinicId(), calendarsUpdateModel.getUuid())
                .stream()
                .map(calendarEntity -> mapper.map(calendarEntity, CalendarModel.class))
                .collect(Collectors.toList());
        List<CalendarModel> toBeAttached = getNewCalendarList(list, calendarsUpdateModel.getMarkAsAttached());
        attachCalendarWithClinicUseCase.attach(toBeAttached.stream().map(model -> model.getId()).collect(Collectors.toList())
                , calendarsUpdateModel.getClinicId()
                , calendarsUpdateModel.getUuid());

        unAttachCalendarWithClinicUseCase.unattached(calendarsUpdateModel.getMarkAsUnAttached().stream().map(model -> model.getId()).collect(Collectors.toList())
                , calendarsUpdateModel.getClinicId()
                , calendarsUpdateModel.getUuid());

        if (calendarsUpdateModel.getMarkAsPublic().size() > 0) {
            List<CalendarModel> publicCalendars = calendarRepository.getPublicCalendars(calendarsUpdateModel.getUuid())
                    .stream()
                    .map(calendarEntity -> mapper.map(calendarEntity, CalendarModel.class))
                    .collect(Collectors.toList());
            List<CalendarModel> markedAsPublic = getNewCalendarList(publicCalendars, calendarsUpdateModel.getMarkAsPublic());
            ChangeCalendarAccessibility.change(markedAsPublic.stream().map(model -> model.getId()).collect(Collectors.toList())
                    , calendarsUpdateModel.getClinicId()
                    , calendarsUpdateModel.getUuid(),true);
        }

        if (calendarsUpdateModel.getMarkAsNotPublic().size() > 0)
            ChangeCalendarAccessibility.change(calendarsUpdateModel.getMarkAsNotPublic().stream().map(model -> model.getId()).collect(Collectors.toList())
                    , calendarsUpdateModel.getClinicId()
                    , calendarsUpdateModel.getUuid(),false);

    }

    private List<CalendarModel> getNewCalendarList(List<CalendarModel> originalList, List<CalendarModel> newList) {
        return newList.stream()
                .filter(entity -> !originalList.contains(entity))
                .collect(Collectors.toList());
    }
}
