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
        if (schedulerSettingsOptional.isPresent())
            return mapper.map(schedulerSettingsOptional.get().getSettings(), SchedulerSettings.class);
        else
            return new SchedulerSettings();

    }
}
