package com.cob.emr.controller.patient.document;

import com.cob.emr.entity.patient.PatientCaseDocument;
import com.cob.emr.model.patient.document.EDocumentUploadRequest;
import com.cob.emr.response.ResponseHandler;
import com.cob.emr.service.patient.document.PatientCaseDocumentUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/patient/document")
public class PatientCaseDocumentController {

    @Autowired
    private PatientCaseDocumentUploadService documentUploadService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> upload(@ModelAttribute EDocumentUploadRequest request) throws IOException {
        return ResponseHandler.generateResponse(
                "Successfully uploaded document!",
                HttpStatus.OK,
                documentUploadService.upload(request));
    }

    @GetMapping("/find/patient/{patientId}/case/{caseUUID}")
    public ResponseEntity<Object> findByPatientId(
            @PathVariable("patientId") Long patientId,
            @PathVariable("caseUUID") String caseUUID) {
        return ResponseHandler.generateResponse(
                "Successfully retrieved documents!",
                HttpStatus.OK,
                documentUploadService.findByPatientId(patientId, caseUUID));
    }

    @GetMapping("/download/{documentId}")
    public ResponseEntity<byte[]> download(@PathVariable("documentId") Long documentId) {
        PatientCaseDocument document = documentUploadService.download(documentId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(document.getContentType()))
                .contentLength(document.getFileSize())
                .body(document.getFileData());
    }

    @PutMapping(value = "/update/{documentId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> update(
            @PathVariable("documentId") Long documentId,
            @ModelAttribute EDocumentUploadRequest request) throws IOException {
        return ResponseHandler.generateResponse(
                "Successfully updated document!",
                HttpStatus.OK,
                documentUploadService.update(documentId, request));
    }

    @DeleteMapping("/{documentId}")
    public ResponseEntity<Object> delete(@PathVariable("documentId") Long documentId) {
        documentUploadService.delete(documentId);
        return ResponseHandler.generateResponse(
                "Successfully deleted document!",
                HttpStatus.OK,
                documentId);
    }
}
