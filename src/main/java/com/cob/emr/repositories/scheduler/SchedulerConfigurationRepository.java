package com.cob.emr.repositories.scheduler;

import com.cob.emr.entity.scheduler.SchedulerConfigurationEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SchedulerConfigurationRepository extends CrudRepository<SchedulerConfigurationEntity,Long> {

    Optional<SchedulerConfigurationEntity> findByClinicId(Long clinicId);

    Optional<List<SchedulerConfigurationEntity>> findByOrganization_Id(Long id);


}
