package com.cob.emr.repositories.medical.note;

import com.cob.emr.entity.medical.note.soap.profile.ObjectiveProfileEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ObjectiveProfileRepository extends CrudRepository<ObjectiveProfileEntity, Long> {
    Optional<ObjectiveProfileEntity> findByName(String name);
}
