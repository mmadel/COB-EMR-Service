package com.cob.emr.usecases.scheduler;

import com.cob.emr.entity.scheduler.SchedulerConfigurationEntity;
import com.cob.emr.model.scheduler.SchedulerConfiguration;
import com.cob.emr.repositories.scheduler.SchedulerConfigurationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateSchedulerConfigurationUseCase {
    @Autowired
    SchedulerConfigurationRepository schedulerConfigurationRepository;
    @Autowired
    ModelMapper mapper;
    public Long configure(SchedulerConfiguration schedulerConfiguration){
        SchedulerConfigurationEntity toBeCreated = mapper.map(schedulerConfiguration, SchedulerConfigurationEntity.class);
        return schedulerConfigurationRepository.save(toBeCreated).getId();
    }
}
