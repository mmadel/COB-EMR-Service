package com.cob.emr.model.patient.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
public class EDocumentUploadRequest {

    private String documentType;
    private String nameOfDocument;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfReceipt;
    private String assignedCase;
    private Long patientId;
    private String caseId;
    private MultipartFile file;
}
