package com.cob.emr.usecases.scheduler;

import com.cob.emr.entity.scheduler.SchedulerSettingsEntity;
import com.cob.emr.model.scheduler.SchedulerSettings;
import com.cob.emr.repositories.scheduler.SchedulerSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateSchedulerSettingsBySelectedCalendarsUseCase {
    @Autowired
    SchedulerSettingsRepository repository;

    public void update(SchedulerSettings model) {
        SchedulerSettingsEntity schedulerSettingsEntity = repository.findById(model.getId())
                .orElseThrow(() -> new IllegalArgumentException("Scheduler Settings with id "
                        + model.getId() + " not found"));
        schedulerSettingsEntity.getSettings()
                .setSelectedCalendarsId(model.getSelectedCalendarsId());
        repository.save(schedulerSettingsEntity);
    }
}
