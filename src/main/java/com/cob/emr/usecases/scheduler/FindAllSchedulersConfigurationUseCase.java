package com.cob.emr.usecases.scheduler;

import com.cob.emr.model.scheduler.SchedulerConfiguration;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.scheduler.SchedulerConfigurationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindAllSchedulersConfigurationUseCase {
    @Autowired
    SchedulerConfigurationRepository schedulerConfigurationRepository;
    @Autowired
    ModelMapper mapper;
    @Autowired
    ClinicRepository clinicRepository;
    public List<SchedulerConfiguration> find(Long configurationId) {
        return schedulerConfigurationRepository.findByOrganization_Id(configurationId).get()
                .stream().map(schedulerConfiguration -> {
                    SchedulerConfiguration configuration =mapper.map(schedulerConfiguration, SchedulerConfiguration.class);
                    configuration.setClinicName(clinicRepository.findById(configuration.getClinicId()).get().getName());
                    return configuration;
                })
                .collect(Collectors.toList());
    }
}
