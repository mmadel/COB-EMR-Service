package com.cob.emr.usecases.clinic;

import com.cob.emr.model.clinic.ClinicModel;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.scheduler.SchedulerConfigurationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class FindNotConfigurableSchedulerClinicsUseCase {
    @Autowired
    ClinicRepository clinicRepository;
    @Autowired
    SchedulerConfigurationRepository schedulerConfigurationRepository;
    @Autowired
    ModelMapper mapper;

    public List<ClinicModel> find(Long organizationId) {
        List<Long> clinicIds = clinicRepository.findByOrganization(organizationId)
                .stream()
                .map(clinic -> clinic.getId())
                .collect(Collectors.toList());
        List<Long> configureClinicIds = schedulerConfigurationRepository.findByOrganization_Id(organizationId)
                .get().stream()
                .map(schedulerConfigurationEntity -> schedulerConfigurationEntity.getClinicId())
                .collect(Collectors.toList());
        List<Long> notConfigureClinicIds = clinicIds.stream()
                .filter(id -> !configureClinicIds.contains(id))
                .collect(Collectors.toList());

        return StreamSupport.stream(clinicRepository.findAllById(notConfigureClinicIds).spliterator(), false)
                .map(clinic -> mapper.map(clinic,ClinicModel.class))
                .collect(Collectors.toList());
    }
}
