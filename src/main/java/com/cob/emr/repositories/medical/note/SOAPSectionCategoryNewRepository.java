package com.cob.emr.repositories.medical.note;

import com.cob.emr.entity.medical.note.soap.field.NewSOAPSectionCategoryEntity;
import com.cob.emr.entity.medical.note.soap.field.SOAPSectionCategoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SOAPSectionCategoryNewRepository extends CrudRepository<NewSOAPSectionCategoryEntity, Long> {
    Optional<NewSOAPSectionCategoryEntity> findByName(String name);

    @Query("SELECT s FROM SOAPSectionCategoryEntity s WHERE s.name LIKE %:keyword%")
    List<NewSOAPSectionCategoryEntity> findByNameContaining(@Param("keyword") String keyword);
}
