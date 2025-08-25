package com.cob.emr.repositories.medical.note.test;

import com.cob.emr.entity.medical.note.test.OMTTestEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OMTTestEntityRepository extends CrudRepository<OMTTestEntity, Long> {
    Optional<OMTTestEntity> findByTestName(String name);
}
