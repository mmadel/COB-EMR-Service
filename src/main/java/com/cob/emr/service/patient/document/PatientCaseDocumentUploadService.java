package com.cob.emr.service.patient.document;

import com.cob.emr.entity.patient.PatientCaseDocument;
import com.cob.emr.model.patient.document.EDocumentUploadRequest;
import com.cob.emr.model.patient.document.PatientCaseDocumentModel;
import com.cob.emr.model.patient.document.PatientCaseDocumentResponse;

import java.io.IOException;

public interface PatientCaseDocumentUploadService {

    PatientCaseDocumentModel upload(EDocumentUploadRequest request) throws IOException;

    PatientCaseDocumentResponse findByPatientId(Long patientId, String caseUUID);

    PatientCaseDocument download(Long documentId);

    PatientCaseDocumentModel update(Long documentId, EDocumentUploadRequest request) throws IOException;

    void delete(Long documentId);
}
