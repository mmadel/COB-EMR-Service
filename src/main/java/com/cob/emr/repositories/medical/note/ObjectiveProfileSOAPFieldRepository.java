package com.cob.emr.repositories.medical.note;

import com.cob.emr.entity.medical.note.soap.field.NewSOAPFieldEntity;
import com.cob.emr.entity.medical.note.soap.field.ObjectiveProfileSOAPField;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ObjectiveProfileSOAPFieldRepository extends CrudRepository<ObjectiveProfileSOAPField, Long> {

    @Query("select opf.soapField from ObjectiveProfileSOAPField opf where  opf.objectiveProfile.name =:profileName")
    List<NewSOAPFieldEntity> findFieldsByProfileName(@Param("profileName") String profileName);

    @Modifying
    @Query("delete from ObjectiveProfileSOAPField opf where  opf.soapField.id in :ids and opf.objectiveProfile.id =:profileId")
    void deleteFieldsFromProfile(@Param("ids") List<Long> ids ,@Param("profileId")  Long profileId);
}
