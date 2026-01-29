package com.cob.emr.controller.audit;

import com.cob.emr.model.audit.AuditLogMapper;
import com.cob.emr.model.audit.AuditLogModel;
import com.cob.emr.repositories.audit.AuditLogRepository;
import com.cob.emr.repositories.audit.AuditLogSpecification;
import com.cob.emr.response.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneOffset;

@RestController
@RequestMapping("/audit")
public class AuditLogController {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private AuditLogMapper auditLogMapper;

    @GetMapping("/entity/{entityName}/{entityId}")
    public ResponseEntity<Object> getByEntity(
            @PathVariable String entityName,
            @PathVariable Long entityId,
            @RequestParam(name = "offset") int offset,
            @RequestParam(name = "limit") int limit) {
        Pageable paging = PageRequest.of(offset, limit, Sort.by("performedAt").descending());
        Page<AuditLogModel> result = auditLogMapper.toModelPage(
                auditLogRepository.findByEntityNameAndEntityId(entityName, entityId, paging));
        return ResponseHandler.generateResponse(
                "Successfully retrieved audit trail!",
                HttpStatus.OK, result);
    }

    @GetMapping("/clinic/{clinicId}")
    public ResponseEntity<Object> getByClinic(
            @PathVariable Long clinicId,
            @RequestParam(name = "offset") int offset,
            @RequestParam(name = "limit") int limit,
            @RequestParam(name = "performedByUuid", required = false) String performedByUuid,
            @RequestParam(name = "patientId", required = false) Long patientId,
            @RequestParam(name = "startDate", required = false) String startDate,
            @RequestParam(name = "endDate", required = false) String endDate) {
        Pageable paging = PageRequest.of(offset, limit, Sort.by("performedAt").descending());

        boolean hasFilters = performedByUuid != null || patientId != null
                || startDate != null || endDate != null;

        Page<AuditLogModel> result;
        if (hasFilters) {
            Long startMillis = startDate != null
                    ? LocalDate.parse(startDate).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
                    : null;
            Long endMillis = endDate != null
                    ? LocalDate.parse(endDate).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
                    : null;
            result = auditLogMapper.toModelPage(
                    auditLogRepository.findAll(
                            AuditLogSpecification.withFilters(clinicId, performedByUuid, patientId, startMillis, endMillis),
                            paging));
        } else {
            result = auditLogMapper.toModelPage(
                    auditLogRepository.findByClinicId(clinicId, paging));
        }

        return ResponseHandler.generateResponse(
                "Successfully retrieved clinic audit trail!",
                HttpStatus.OK, result);
    }

    @GetMapping("/clinic/{clinicId}/entity/{entityName}")
    public ResponseEntity<Object> getByClinicAndEntity(
            @PathVariable Long clinicId,
            @PathVariable String entityName,
            @RequestParam(name = "offset") int offset,
            @RequestParam(name = "limit") int limit) {
        Pageable paging = PageRequest.of(offset, limit, Sort.by("performedAt").descending());
        Page<AuditLogModel> result = auditLogMapper.toModelPage(
                auditLogRepository.findByClinicIdAndEntityName(clinicId, entityName, paging));
        return ResponseHandler.generateResponse(
                "Successfully retrieved entity audit trail!",
                HttpStatus.OK, result);
    }

    @GetMapping("/clinic/{clinicId}/entity/{entityName}/{entityId}")
    public ResponseEntity<Object> getByClinicAndEntityAndId(
            @PathVariable Long clinicId,
            @PathVariable String entityName,
            @PathVariable Long entityId,
            @RequestParam(name = "offset") int offset,
            @RequestParam(name = "limit") int limit) {
        Pageable paging = PageRequest.of(offset, limit, Sort.by("performedAt").descending());
        Page<AuditLogModel> result = auditLogMapper.toModelPage(
                auditLogRepository.findByClinicIdAndEntityNameAndEntityId(clinicId, entityName, entityId, paging));
        return ResponseHandler.generateResponse(
                "Successfully retrieved audit trail!",
                HttpStatus.OK, result);
    }
}
