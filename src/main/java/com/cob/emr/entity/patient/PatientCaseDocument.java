package com.cob.emr.entity.patient;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity(name = "patient_case_document")
@Getter
@Setter
public class PatientCaseDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid", unique = true, nullable = false, updatable = false)
    private String uuid;

    @Column(name = "document_type")
    private String documentType;

    @Column(name = "name_of_document")
    private String nameOfDocument;

    @Column(name = "date_of_receipt")
    private LocalDate dateOfReceipt;

    @Column(name = "assigned_case")
    private String assignedCase;

    @Column(name = "case_id")
    private String caseId;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "content_type")
    private String contentType;

    @Lob
    @Column(name = "file_data", columnDefinition = "LONGBLOB")
    private byte[] fileData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

    @PrePersist
    public void beforeSaving() {
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
        }
        createdAt = new Date().getTime();
        updatedAt = new Date().getTime();
    }

    @PreUpdate
    public void beforeUpdate() {
        updatedAt = new Date().getTime();
    }
}
