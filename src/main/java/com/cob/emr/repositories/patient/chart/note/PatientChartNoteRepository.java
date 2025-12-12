package com.cob.emr.repositories.patient.chart.note;

import com.cob.emr.entity.patient.chart.note.PatientChartNote;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface PatientChartNoteRepository extends PagingAndSortingRepository<PatientChartNote, Long> {
    Optional<PatientChartNote> findByPatientCaseId(Long patientCaseId);
}
