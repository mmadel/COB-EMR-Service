package com.cob.emr.repositories.medical.note;

import com.cob.emr.entity.medical.note.soap.field.NewSOAPFieldEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SOAPFieldNewRepository extends CrudRepository<NewSOAPFieldEntity, Long> {
    @Query(value = "SELECT sf FROM NewSOAPFieldEntity sf WHERE JSON_EXTRACT(sf.value, '$.idField.id') = :id")
    NewSOAPFieldEntity findByIdFieldId(@Param("id") String id);

    @Query(value = "SELECT sf FROM NewSOAPFieldEntity sf WHERE JSON_EXTRACT(sf.value, '$.parentField') = :id")
    List<NewSOAPFieldEntity> findChild(@Param("id") String id);

    @Query("SELECT s FROM NewSOAPFieldEntity s WHERE s.fieldDisplayName = :displayName")
    Optional<NewSOAPFieldEntity> findByFieldDisplayName(@Param("displayName") String displayName);

    @Query("SELECT s FROM NewSOAPFieldEntity s WHERE s.fieldName = :id")
    Optional<NewSOAPFieldEntity> findByFieldId(@Param("id") String id);
}
