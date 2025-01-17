package com.cob.emr.usecases.scheduler;

import com.cob.emr.entity.scheduler.SchedulerUserSettingsEntity;
import com.cob.emr.model.scheduler.SchedulerUserSettings;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.scheduler.SchedulerUserSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UpdateSchedulerSettingsBySelectedCalendarsUseCase {
    @Autowired
    SchedulerUserSettingRepository repository;
    @Autowired
    ClinicRepository clinicRepository;

    public void update(SchedulerUserSettings model) {
        SchedulerUserSettingsEntity entity;
        Optional<SchedulerUserSettingsEntity> optional = repository.findByClinic_IdAndUser(model.getClinicId(), model.getUser());
        if (optional.isPresent())
            entity = optional.get();
        else {
            entity = new SchedulerUserSettingsEntity();
            entity.setUser(model.getUser());
            entity.setClinic(clinicRepository.findById(model.getClinicId()).get());
        }
        entity.setSelectedCalendars(model.getSelectedCalendars());
        repository.save(entity);
    }
}
