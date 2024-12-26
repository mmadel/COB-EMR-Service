package com.cob.emr.usecases.scheduler;

import com.cob.emr.entity.scheduler.SchedulerSettingsEntity;
import com.cob.emr.model.scheduler.SchedulerSettings;
import com.cob.emr.repositories.scheduler.SchedulerSettingsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FindSchedulerSettingsForClinicUseCase {
    @Autowired
    SchedulerSettingsRepository repository;
    @Autowired
    ModelMapper mapper;

    public SchedulerSettings find(Long clinicId) {
        Optional<SchedulerSettingsEntity> schedulerSettingsOptional = repository.findByClinic_Id(clinicId);
        if (schedulerSettingsOptional.isPresent()){
            SchedulerSettingsEntity schedulerSettingsEntity = schedulerSettingsOptional.get();
            SchedulerSettings schedulerSettings = mapper.map(schedulerSettingsEntity.getSettings(), SchedulerSettings.class);
            schedulerSettings.setId(schedulerSettingsEntity.getId());
            return schedulerSettings;
        }
        else
            return new SchedulerSettings();

    }
}
