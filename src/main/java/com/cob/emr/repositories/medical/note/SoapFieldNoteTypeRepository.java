package com.cob.emr.repositories.medical.note;

import com.cob.emr.entity.medical.note.soap.field.SoapFieldNoteType;
import org.springframework.data.repository.CrudRepository;

public interface SoapFieldNoteTypeRepository extends CrudRepository<SoapFieldNoteType, Long> {
}
