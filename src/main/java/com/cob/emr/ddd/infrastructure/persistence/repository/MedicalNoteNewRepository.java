package com.cob.emr.ddd.infrastructure.persistence.repository;

import com.cob.emr.ddd.infrastructure.persistence.entity.MedicalNoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for MedicalNoteEntity
 * Supports Single Table Inheritance for all note types
 */
@Repository
public interface MedicalNoteNewRepository extends JpaRepository<MedicalNoteEntity, Long> {

    // ==================== Basic Queries ====================

    /**
     * Find all notes for a patient case, ordered by visit number
     */
    List<MedicalNoteEntity> findByPatientCaseIdOrderByVisitNumberAsc(Long patientCaseId);

    /**
     * Find a specific note by patient case and visit number
     */
    Optional<MedicalNoteEntity> findByPatientCaseIdAndVisitNumber(Long patientCaseId, Integer visitNumber);

    /**
     * Find all notes by type for a patient case
     */
    List<MedicalNoteEntity> findByPatientCaseIdAndNoteTypeOrderByVisitNumberAsc(
            Long patientCaseId,
            MedicalNoteEntity.NoteType noteType
    );

    /**
     * Find all notes within a date range
     */
    List<MedicalNoteEntity> findByDateOfServiceBetweenOrderByDateOfServiceDesc(
            LocalDate startDate,
            LocalDate endDate
    );

    /**
     * Find all draft notes for a patient case
     */
    List<MedicalNoteEntity> findByPatientCaseIdAndStatusOrderByVisitNumberAsc(
            Long patientCaseId,
            MedicalNoteEntity.NoteStatus status
    );

    // ==================== Query Using Extracted Columns ====================

    /**
     * Find notes with pain scale above threshold
     */
    List<MedicalNoteEntity> findByPatientCaseIdAndPainScaleGreaterThanEqual(
            Long patientCaseId,
            Integer painScale
    );

    /**
     * Find notes by work status
     */
    List<MedicalNoteEntity> findByWorkStatusAndDateOfServiceBetween(
            String workStatus,
            LocalDate startDate,
            LocalDate endDate
    );

    /**
     * Find notes by complexity level
     */
    @Query("SELECT n FROM MedicalNoteEntity n WHERE n.complexityLevel = :complexity " +
            "AND n.dateOfService BETWEEN :startDate AND :endDate")
    List<MedicalNoteEntity> findByComplexityAndDateRange(
            @Param("complexity") String complexity,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    /**
     * Find non-compliant patients
     */
    List<MedicalNoteEntity> findByIsPatientCompliantFalseAndDateOfServiceAfter(LocalDate date);

    /**
     * Get latest note for patient case
     */
    Optional<MedicalNoteEntity> findFirstByPatientCaseIdOrderByVisitNumberDesc(Long patientCaseId);

    /**
     * Count notes by type for patient case
     */
    Long countByPatientCaseIdAndNoteType(Long patientCaseId, MedicalNoteEntity.NoteType noteType);

    /**
     * Check if Initial Evaluation exists for patient case
     */
    boolean existsByPatientCaseIdAndNoteType(
            Long patientCaseId,
            MedicalNoteEntity.NoteType noteType
    );

    // ==================== Reporting Queries ====================

    /**
     * Get total billing units for a date range
     */
    @Query("SELECT COALESCE(SUM(n.totalBillingUnits), 0) FROM MedicalNoteEntity n " +
            "WHERE n.dateOfService BETWEEN :startDate AND :endDate " +
            "AND n.status = 'FINAL'")
    Integer getTotalBillingUnitsForDateRange(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    /**
     * Get average pain scale for patient case
     */
    @Query("SELECT AVG(n.painScale) FROM MedicalNoteEntity n " +
            "WHERE n.patientCaseId = :patientCaseId " +
            "AND n.painScale IS NOT NULL")
    Double getAveragePainScale(@Param("patientCaseId") Long patientCaseId);
}
