package com.cob.emr.repositories.medical.note;

import com.cob.emr.entity.medical.note.soap.field.SOAPSectionCategoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SOAPSectionCategoryRepository extends CrudRepository<SOAPSectionCategoryEntity,Long> {
    Optional<SOAPSectionCategoryEntity> findByName(String name);

    @Query("SELECT s FROM SOAPSectionCategoryEntity s WHERE s.name LIKE %:keyword%")
    List<SOAPSectionCategoryEntity> findByNameContaining(@Param("keyword") String keyword);
}
