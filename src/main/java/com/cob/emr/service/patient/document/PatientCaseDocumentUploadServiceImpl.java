package com.cob.emr.service.patient.document;

import com.cob.emr.entity.patient.Patient;
import com.cob.emr.entity.patient.PatientCaseDocument;
import com.cob.emr.model.patient.document.EDocumentUploadRequest;
import com.cob.emr.model.patient.document.PatientCaseDocumentModel;
import com.cob.emr.model.patient.document.PatientCaseDocumentResponse;
import com.cob.emr.repositories.patient.PatientRepository;
import com.cob.emr.repositories.patient.document.PatientCaseDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PatientCaseDocumentUploadServiceImpl implements PatientCaseDocumentUploadService {

    @Autowired
    private PatientCaseDocumentRepository documentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public PatientCaseDocumentModel upload(EDocumentUploadRequest request) throws IOException {

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found with id: " + request.getPatientId()));

        MultipartFile file = request.getFile();

        PatientCaseDocument document = new PatientCaseDocument();
        document.setDocumentType(request.getDocumentType());
        document.setNameOfDocument(request.getNameOfDocument());
        document.setDateOfReceipt(request.getDateOfReceipt());
        document.setAssignedCase(request.getAssignedCase());
        document.setCaseId(request.getCaseId());
        document.setFileName(file.getOriginalFilename());
        document.setFileSize(file.getSize());
        document.setContentType(file.getContentType());
        document.setFileData(file.getBytes());
        document.setPatient(patient);

        PatientCaseDocument savedDocument = documentRepository.save(document);

        return mapToModel(savedDocument);
    }

    @Override
    public PatientCaseDocumentResponse findByPatientId(Long patientId, String caseUUID) {
        List<PatientCaseDocument> documents = documentRepository.findByPatientId(patientId);

        List<PatientCaseDocumentModel> allList = documents.stream()
                .filter(doc -> "all".equals(doc.getAssignedCase()))
                .map(this::mapToModel)
                .collect(Collectors.toList());

        List<PatientCaseDocumentModel> caseList = documents.stream()
                .filter(doc -> caseUUID.equals(doc.getAssignedCase()))
                .map(this::mapToModel)
                .collect(Collectors.toList());

        PatientCaseDocumentResponse response = new PatientCaseDocumentResponse();
        response.setAllList(allList);
        response.setCaseList(caseList);
        return response;
    }

    @Override
    public PatientCaseDocument download(Long documentId) {
        return documentRepository.findById(documentId)
                .orElseThrow(() -> new IllegalArgumentException("Document not found with id: " + documentId));
    }

    @Override
    public PatientCaseDocumentModel update(Long documentId, EDocumentUploadRequest request) throws IOException {

        PatientCaseDocument document = documentRepository.findById(documentId)
                .orElseThrow(() -> new IllegalArgumentException("Document not found with id: " + documentId));

        document.setDocumentType(request.getDocumentType());
        document.setNameOfDocument(request.getNameOfDocument());
        document.setDateOfReceipt(request.getDateOfReceipt());
        document.setAssignedCase(request.getAssignedCase());
        document.setCaseId(request.getCaseId());

        MultipartFile file = request.getFile();
        if (file != null && !file.isEmpty()) {
            document.setFileName(file.getOriginalFilename());
            document.setFileSize(file.getSize());
            document.setContentType(file.getContentType());
            document.setFileData(file.getBytes());
        }

        PatientCaseDocument savedDocument = documentRepository.save(document);
        return mapToModel(savedDocument);
    }

    @Override
    public void delete(Long documentId) {
        PatientCaseDocument document = documentRepository.findById(documentId)
                .orElseThrow(() -> new IllegalArgumentException("Document not found with id: " + documentId));

        documentRepository.delete(document);
    }

    private PatientCaseDocumentModel mapToModel(PatientCaseDocument document) {
        PatientCaseDocumentModel model = new PatientCaseDocumentModel();
        model.setId(document.getId());
        model.setUuid(document.getUuid());
        model.setDocumentType(document.getDocumentType());
        model.setNameOfDocument(document.getNameOfDocument());
        model.setDateOfReceipt(document.getDateOfReceipt());
        model.setAssignedCase(document.getAssignedCase());
        model.setPatientId(document.getPatient().getId());
        model.setCaseId(document.getCaseId());
        model.setFileName(document.getFileName());
        model.setFileSize(document.getFileSize());
        model.setContentType(document.getContentType());
        model.setCreatedAt(document.getCreatedAt());
        model.setUpdatedAt(document.getUpdatedAt());
        return model;
    }
}
