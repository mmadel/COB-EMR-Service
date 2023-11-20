package com.cob.emr.repositories.scheduler;

import com.cob.emr.entity.scheduler.SchedulerConfigurationEntity;
import org.springframework.data.repository.CrudRepository;

public interface SchedulerConfigurationRepository extends CrudRepository<SchedulerConfigurationEntity,Long> {
}
