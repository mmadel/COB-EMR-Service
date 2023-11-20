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
        SchedulerConfigurationEntity schedulerConfiguration ;
        if(!schedulerConfigurationRepository.findByClinicId(clinicId).isEmpty())
        schedulerConfiguration = schedulerConfigurationRepository.findByClinicId(clinicId).get();
        else{
            schedulerConfiguration = new SchedulerConfigurationEntity();
            schedulerConfiguration.setClinicId(clinicId);
            schedulerConfiguration.setStartHour(8L);
            schedulerConfiguration.setEndHour(19L);
        }
        return mapper.map(schedulerConfiguration, SchedulerConfiguration.class);
    }
}
