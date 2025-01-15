package com.cob.emr.usecases.calendar.tmp;

import com.cob.emr.entity.calendar.CalendarEntity;
import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.model.calendar.CalendarModel;
import com.cob.emr.repositories.calendar.CalendarRepository;
import com.cob.emr.repositories.clinic.ClinicRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateCalendarUseCaseq {
    @Autowired
    ClinicRepository clinicRepository;
    @Autowired
    CalendarRepository calendarRepository;
    @Autowired
    ModelMapper mapper;

    public Long create(CalendarModel model) {
        Clinic clinic = clinicRepository.findById(model.getClinicId())
                .orElseThrow(() -> new IllegalArgumentException("Clinic not found during calendar creation"));
        CalendarEntity calendarEntity = mapper.map(model, CalendarEntity.class);
        calendarEntity.setCreatedBy(model.getCreatedBy());
        calendarEntity.setClinic(clinic);
        return calendarRepository.save(calendarEntity).getId();
    }
}
