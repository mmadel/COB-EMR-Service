package com.cob.emr.usecases.calendar;

import com.cob.emr.model.calendar.CalendarModel;
import com.cob.emr.repositories.calendar.CalendarClinicRepository;
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
    ModelMapper mapper;

    public List<CalendarModel> get(Long clinicId, String uuid) {
        return repository.findCalendars(clinicId, uuid).stream()
                .map(calendarEntity -> mapper.map(calendarEntity, CalendarModel.class))
                .collect(Collectors.toList());
    }
}
