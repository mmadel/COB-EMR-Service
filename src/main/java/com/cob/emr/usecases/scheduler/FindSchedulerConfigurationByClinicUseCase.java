package com.cob.emr.usecases.scheduler;

import com.cob.emr.entity.scheduler.SchedulerConfigurationEntity;
import com.cob.emr.model.scheduler.SchedulerConfiguration;
import com.cob.emr.repositories.scheduler.SchedulerConfigurationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FindSchedulerConfigurationByClinicUseCase {
    @Autowired
    SchedulerConfigurationRepository schedulerConfigurationRepository;
    @Autowired
    ModelMapper mapper;

    public SchedulerConfiguration find(Long clinicId) {
        SchedulerConfigurationEntity schedulerConfiguration = schedulerConfigurationRepository.findByClinicId(clinicId).orElseThrow(() -> new IllegalArgumentException("scheduler configuration for clinic not found"));
        return mapper.map(schedulerConfiguration, SchedulerConfiguration.class);
    }
}
