package com.cob.emr.model.patient.document;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PatientCaseDocumentModel {

    private Long id;
    private String uuid;
    private String documentType;
    private String nameOfDocument;
    private LocalDate dateOfReceipt;
    private String assignedCase;
    private Long patientId;
    private String caseId;
    private String fileName;
    private Long fileSize;
    private String contentType;
    private Long createdAt;
    private Long updatedAt;
}
