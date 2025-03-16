package com.cob.emr.repositories.medical.note;

import com.cob.emr.entity.medical.note.soap.field.SOAPFieldEntity;
import org.springframework.data.repository.CrudRepository;

public interface SOAPFieldRepository extends CrudRepository<SOAPFieldEntity, Long> {
}
