package com.cob.emr.repositories.scheduler;

import com.cob.emr.entity.scheduler.SchedulerSettingsEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SchedulerSettingsRepository extends CrudRepository<SchedulerSettingsEntity, Long> {
    Optional<SchedulerSettingsEntity> findByClinic_Id(Long clinicId);
    void deleteByClinic_Id(Long clinicId);
}
