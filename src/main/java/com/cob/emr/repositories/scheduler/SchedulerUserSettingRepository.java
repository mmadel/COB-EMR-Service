package com.cob.emr.repositories.scheduler;

import com.cob.emr.entity.scheduler.SchedulerUserSettingsEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SchedulerUserSettingRepository extends CrudRepository<SchedulerUserSettingsEntity, Long> {
    Optional<SchedulerUserSettingsEntity> findByClinic_IdAndUser(Long clinicId, String uuid);
    void deleteByClinic_Id(Long clinicId);
}
