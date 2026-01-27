package com.cob.emr.repositories.medical.note;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.enums.MedicalNoteStatus;
import com.cob.emr.enums.MedicalNoteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MedicalNoteRepository extends JpaRepository<MedicalNote, Long> {


    Optional<List<MedicalNote>> findByPatientCase_IdAndType(Long caseId, MedicalNoteType type);

    Optional<MedicalNote> findByPatientCase_IdAndId(Long caseId, Long medicalNoteId);

    Optional<List<MedicalNote>> findByPatientCase_Id(Long caseId);

    Optional<MedicalNote> findByIdAndType(Long id, MedicalNoteType type);

    Optional<MedicalNote> findByPatientCase_IdAndTypeAndStatus(Long caseId, MedicalNoteType type, MedicalNoteStatus status);

    @Query("SELECT m FROM MedicalNote m " +
            "WHERE m.patientCase.id = :patientCase " +
            "AND m.type = :type " +
            "AND m.status = :status " +
            "ORDER BY m.createdAt DESC")
    List<MedicalNote> findRecentNoteByTypeAndStatusAndCase(
            @Param("type") MedicalNoteType type,
            @Param("status") MedicalNoteStatus status,
            @Param("patientCase") Long patientCase
    );

    Optional<MedicalNote> findTopByTypeAndStatusAndPatientCase_IdOrderByCreatedAtDesc(
            MedicalNoteType type,
            MedicalNoteStatus status,
            Long patientCaseId
    );
    @Query("SELECT MAX(m.visitNumber) FROM MedicalNote m WHERE m.patientCase.id = :caseId")
    Integer findMaxVisitByCaseId(@Param("caseId") Long caseId);

    void deleteByPatientCase_Patient_Id(Long patientId);
}
