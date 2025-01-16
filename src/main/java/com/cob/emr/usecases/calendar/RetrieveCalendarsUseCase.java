package com.cob.emr.usecases.calendar;

import com.cob.emr.entity.calendar.CalendarEntity;
import com.cob.emr.model.calendar.CalendarModel;
import com.cob.emr.model.response.CalendarResponse;
import com.cob.emr.repositories.calendar.CalendarClinicRepository;
import com.cob.emr.repositories.calendar.CalendarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RetrieveCalendarsUseCase {
    @Autowired
    private CalendarRepository repository;
    @Autowired
    private CalendarClinicRepository calendarClinicRepository;
    @Autowired
    ModelMapper mapper;

    public CalendarResponse retrieve(Pageable paging, String uuid, Long clinicId) {
        List<CalendarEntity> attachedCalendars = calendarClinicRepository.findCalendars(clinicId, uuid);
        Page<CalendarEntity> pages = repository.getCalendars(paging, uuid);
        long total = (pages).getTotalElements();
        List<CalendarModel> records = pages.stream().map(calendarEntity -> {
            CalendarModel model = mapper.map(calendarEntity, CalendarModel.class);
            model.setAttached(isAttachedCalendar(attachedCalendars, calendarEntity));
            return model;
        }).collect(Collectors.toList());

        return null;
    }

    private boolean isAttachedCalendar(List<CalendarEntity> attachedCalendars, CalendarEntity calendarEntity) {
        boolean isAttached = false;
        for (CalendarEntity calendar : attachedCalendars) {
            if (calendar.getId().equals(calendarEntity.getId())) {
                isAttached = true;
                break;
            }
        }
        return isAttached;
    }
}