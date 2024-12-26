package com.cob.emr.usecases.scheduler;

import com.cob.emr.entity.scheduler.SchedulerSettingsEntity;
import com.cob.emr.model.scheduler.SchedulerSettings;
import com.cob.emr.repositories.scheduler.SchedulerSettingsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FindSchedulerSettingsForClinicUseCase {
    @Autowired
    SchedulerSettingsRepository repository;
    @Autowired
    ModelMapper mapper;
    public SchedulerSettings find(Long clinicId) {
        SchedulerSettingsEntity schedulerSettingsEntity = repository.findByClinic_Id(clinicId)
                .orElseThrow(() ->  new IllegalArgumentException(" Scheduler Setting not found"));
        return mapper.map(schedulerSettingsEntity.getSettings(), SchedulerSettings.class);
    }
}
