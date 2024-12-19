package com.cob.emr.usecases.calendar;

import com.cob.emr.entity.calendar.CalendarEntity;
import com.cob.emr.entity.patient.Patient;
import com.cob.emr.model.calendar.CalendarModel;
import com.cob.emr.model.patient.PatientModel;
import com.cob.emr.model.response.CalendarResponse;
import com.cob.emr.model.response.PatientResponse;
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
    CalendarRepository repository;
    @Autowired
    ModelMapper mapper;

    public CalendarResponse retrieve(Pageable paging, String uuid) {
        Page<CalendarEntity> pages = repository.getCalendars(paging, uuid);
        long total = (pages).getTotalElements();
        List<CalendarModel> records = pages.stream().map(calendarEntity -> {
            CalendarModel model = mapper.map(calendarEntity, CalendarModel.class);
            return model;
        }).collect(Collectors.toList());

        return CalendarResponse.builder()
                .number_of_records(records.size())
                .number_of_matching_records((int) total)
                .records(records)
                .build();
    }
}