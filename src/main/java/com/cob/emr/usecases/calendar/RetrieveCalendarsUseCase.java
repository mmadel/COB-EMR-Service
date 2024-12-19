package com.cob.emr.usecases.calendar;

import com.cob.emr.model.calendar.CalendarModel;
import com.cob.emr.repositories.calendar.CalendarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RetrieveCalendarsUseCase {
    @Autowired
    CalendarRepository repository;
    @Autowired
    ModelMapper mapper;

    public List<CalendarModel> retrieve(String uuid) {
        return repository.getCalendars(uuid).stream()
                .map(calendarEntity -> mapper.map(calendarEntity, CalendarModel.class))
                .collect(Collectors.toList());
    }
}
