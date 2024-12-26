package com.cob.emr.usecases.scheduler;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.scheduler.SchedulerSettingsEntity;
import com.cob.emr.model.scheduler.SchedulerSettings;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.scheduler.SchedulerSettingsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateSchedulerSettingsUseCase {
    @Autowired
    SchedulerSettingsRepository repository;
    @Autowired
    ClinicRepository clinicRepository;
    @Autowired
    ModelMapper mapper;

    public Long create(SchedulerSettings model) {
        Clinic clinic = clinicRepository.findById(model.getClinicId()).orElseThrow(() -> new IllegalArgumentException("Clinic not found"));
        SchedulerSettingsEntity toBeCreated = mapper.map(model, SchedulerSettingsEntity.class);
        toBeCreated.setClinic(clinic);
        return repository.save(toBeCreated).getId();
    }
}
