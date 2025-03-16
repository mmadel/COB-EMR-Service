package com.cob.emr.repositories.medical.note;

import com.cob.emr.entity.medical.note.soap.field.SOAPSectionCategoryEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SOAPSectionCategoryRepository extends CrudRepository<SOAPSectionCategoryEntity,Long> {
    Optional<SOAPSectionCategoryEntity> findByName(String name);
}
