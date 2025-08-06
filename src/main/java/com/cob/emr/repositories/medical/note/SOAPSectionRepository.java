package com.cob.emr.repositories.medical.note;

import com.cob.emr.entity.medical.note.soap.field.SOAPSectionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SOAPSectionRepository extends CrudRepository<SOAPSectionEntity,Long> {
    Optional<SOAPSectionEntity> findByName(String name);
}
