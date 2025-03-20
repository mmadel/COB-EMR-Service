package com.cob.emr.repositories.medical.note;

import com.cob.emr.entity.medical.note.MedicalNote;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MedicalNoteRepository extends CrudRepository<MedicalNote, Long> {
    Optional<List<MedicalNote>> findByPatientCase_Id(Long caseId);
}
