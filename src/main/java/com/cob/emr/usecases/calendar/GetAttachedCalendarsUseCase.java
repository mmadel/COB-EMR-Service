package com.cob.emr.usecases.calendar;

import com.cob.emr.entity.calendar.CalendarEntity;
import com.cob.emr.model.calendar.CalendarModel;
import com.cob.emr.repositories.calendar.CalendarClinicRepository;
import com.cob.emr.repositories.calendar.CalendarRepository;
import com.cob.emr.repositories.calendar.UserCalendarAttachmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetAttachedCalendarsUseCase {
    @Autowired
    CalendarClinicRepository repository;
    @Autowired
    CalendarRepository calendarRepository;
    @Autowired
    UserCalendarAttachmentRepository userCalendarAttachmentRepository;
    @Autowired
    ModelMapper mapper;

    public List<CalendarModel> get(Long clinicId, String uuid) {
        return userCalendarAttachmentRepository.findAttached(clinicId, uuid)
                .stream()
                .filter(calendarEntity -> {
                    if (calendarEntity.getCreatedBy().equals(uuid))
                        return true;
                    else {
                        if (calendarEntity.getIsPublic())
                            return true;
                        else
                            return false;
                    }
                })
                .map(calendarEntity -> mapper.map(calendarEntity, CalendarModel.class))
                .collect(Collectors.toList());
    }
}
