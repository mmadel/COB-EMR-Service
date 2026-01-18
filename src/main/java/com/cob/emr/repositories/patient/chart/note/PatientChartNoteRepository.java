package com.cob.emr.repositories.patient.chart.note;

import com.cob.emr.entity.patient.chart.note.PatientChartNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PatientChartNoteRepository extends JpaRepository<PatientChartNote, Long> {
    Optional<List<PatientChartNote>> findByPatientCaseUuid(String patientCaseUuid);
    boolean existsByPatientCaseUuid(String patientCaseUuid);
    boolean existsByPatientCaseUuidAndNoteType(String patientCaseUuid, String noteType);
    Optional<PatientChartNote> findByNoteId(String noteId);
}
