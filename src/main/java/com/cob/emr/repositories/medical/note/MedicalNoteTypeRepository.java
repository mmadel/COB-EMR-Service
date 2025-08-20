package com.cob.emr.repositories.medical.note;

import com.cob.emr.entity.medical.note.soap.field.MedicalNoteTypeEntity;
import org.springframework.data.repository.CrudRepository;

public interface MedicalNoteTypeRepository extends CrudRepository<MedicalNoteTypeEntity, Long> {
}
