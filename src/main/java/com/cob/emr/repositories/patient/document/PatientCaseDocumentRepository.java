package com.cob.emr.repositories.patient.document;

import com.cob.emr.entity.patient.PatientCaseDocument;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface PatientCaseDocumentRepository extends PagingAndSortingRepository<PatientCaseDocument, Long> {

    List<PatientCaseDocument> findByPatientId(Long patientId);

    List<PatientCaseDocument> findByPatientIdAndCaseId(Long patientId, String caseId);

    List<PatientCaseDocument> findByPatientIdAndAssignedCase(Long patientId, String assignedCase);

    Optional<PatientCaseDocument> findByUuid(String uuid);
}
