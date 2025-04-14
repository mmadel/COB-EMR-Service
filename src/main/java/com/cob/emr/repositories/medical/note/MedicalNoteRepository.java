package com.cob.emr.repositories.medical.note;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.enums.MedicalNoteStatus;
import com.cob.emr.enums.MedicalNoteType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MedicalNoteRepository extends CrudRepository<MedicalNote, Long> {
    Optional<List<MedicalNote>> findByPatientCase_Id(Long caseId);
    Optional<MedicalNote> findByIdAndType(Long id , MedicalNoteType type);

    Optional<MedicalNote> findByPatientCase_IdAndTypeAndStatus(Long caseId , MedicalNoteType type , MedicalNoteStatus status);
}
