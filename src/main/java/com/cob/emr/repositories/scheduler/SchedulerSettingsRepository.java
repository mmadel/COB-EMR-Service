package com.cob.emr.repositories.scheduler;

import com.cob.emr.entity.scheduler.SchedulerSettingsEntity;
import org.springframework.data.repository.CrudRepository;

public interface SchedulerSettingsRepository extends CrudRepository<SchedulerSettingsEntity, Long> {
}
