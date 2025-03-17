package com.cob.emr.repositories.medical.note;

import com.cob.emr.entity.medical.note.MedicalNote;
import org.springframework.data.repository.CrudRepository;

public interface MedicalNoteRepository extends CrudRepository<MedicalNote, Long> {
}
