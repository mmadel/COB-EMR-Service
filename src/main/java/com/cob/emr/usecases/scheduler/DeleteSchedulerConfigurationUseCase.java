package com.cob.emr.usecases.scheduler;

import com.cob.emr.repositories.scheduler.SchedulerConfigurationRepository;
import com.cob.emr.repositories.scheduler.SchedulerSettingsRepository;
import com.cob.emr.repositories.scheduler.SchedulerUserSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class DeleteSchedulerConfigurationUseCase {
    @Autowired
    SchedulerConfigurationRepository schedulerConfigurationRepository;
    @Autowired
    SchedulerSettingsRepository schedulerSettingsRepository;
    @Autowired
    SchedulerUserSettingRepository schedulerUserSettingRepository;

    @Transactional
    public void deleteByClinic(Long clinicId) {
        schedulerUserSettingRepository.deleteByClinic_Id(clinicId);
        schedulerSettingsRepository.deleteByClinic_Id(clinicId);
        schedulerConfigurationRepository.deleteByClinicId(clinicId);
    }
}
