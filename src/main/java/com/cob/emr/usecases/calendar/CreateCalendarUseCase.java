package com.cob.emr.usecases.calendar;


import com.cob.emr.entity.calendar.CalendarEntity;
import com.cob.emr.model.calendar.CalendarModel;
import com.cob.emr.repositories.calendar.CalendarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateCalendarUseCase {
    @Autowired
    ModelMapper mapper;
    @Autowired
    CalendarRepository calendarRepository;

    public Long create(CalendarModel model) {
        CalendarEntity calendarEntity = mapper.map(model, CalendarEntity.class);
        return calendarRepository.save(calendarEntity).getId();
    }
}
