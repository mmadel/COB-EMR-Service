package com.cob.emr.repositories.medical.note;

import com.cob.emr.entity.medical.note.soap.field.SOAPFieldEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SOAPFieldRepository extends CrudRepository<SOAPFieldEntity, Long> {
    @Query(value = "SELECT sf FROM SOAPFieldEntity sf WHERE JSON_EXTRACT(sf.value, '$.idField.id') = :id")
    SOAPFieldEntity findByIdFieldId(@Param("id") String id);


    @Query("SELECT s FROM SOAPFieldEntity s WHERE s.fieldDisplayName = :name")
    Optional<SOAPFieldEntity> findByFieldDisplayName(@Param("name") String name);
}
