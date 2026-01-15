package com.cob.emr.repositories.patient.chart.note;

import com.cob.emr.entity.patient.chart.note.PatientChartNote;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface PatientChartNoteRepository extends PagingAndSortingRepository<PatientChartNote, Long> {
    Optional<List<PatientChartNote>> findByPatientCaseId(Long patientCaseId);
    boolean existsByPatientCaseId(Long patientCaseId);
    boolean existsByPatientCaseIdAndNoteType(Long patientCaseId, String noteType);
    Optional<PatientChartNote> findByNoteId(String noteId);
}
