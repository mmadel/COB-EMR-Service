package com.cob.emr.usecases.scheduler;

import com.cob.emr.entity.scheduler.SchedulerUserSettingsEntity;
import com.cob.emr.model.calendar.CalendarModel;
import com.cob.emr.repositories.calendar.CalendarRepository;
import com.cob.emr.repositories.scheduler.SchedulerUserSettingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FindCalendarsBySchedulerUserSettingsUseCase {
    @Autowired
    SchedulerUserSettingRepository repository;
    @Autowired
    CalendarRepository calendarRepository;
    @Autowired
    ModelMapper mapper;

    public List<CalendarModel> find(Long clinicId, String user) {
        Optional<SchedulerUserSettingsEntity> optional = repository.findByClinic_IdAndUser(clinicId, user);
        if (optional.isPresent()) {
            SchedulerUserSettingsEntity schedulerUserSettings = optional.get();
            return calendarRepository.findAllById(schedulerUserSettings.getSelectedCalendars())
                    .stream()
                    .map(calendarEntity -> mapper.map(calendarEntity, CalendarModel.class))
                    .collect(Collectors.toList());
        } else
            return new ArrayList<>();
    }
}
