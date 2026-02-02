package com.cob.emr.usecases.audit;

import com.cob.emr.entity.audit.SignatureAuditLog;
import com.cob.emr.enums.SignatureAuditEvent;
import com.cob.emr.repositories.audit.SignatureAuditLogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class SignatureAuditService {

    private static final Logger log = LoggerFactory.getLogger(SignatureAuditService.class);

    @Autowired
    private SignatureAuditLogRepository signatureAuditLogRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void logSuccess(SignatureAuditEvent event, Long doctorId, Long tokenId, Map<String, Object> details) {
        persist(event, doctorId, tokenId, true, null, details);
    }

    public void logFailure(SignatureAuditEvent event, Long doctorId, Long tokenId, String errorCode, Map<String, Object> details) {
        persist(event, doctorId, tokenId, false, errorCode, details);
    }

    private void persist(SignatureAuditEvent event, Long doctorId, Long tokenId,
                         boolean success, String errorCode, Map<String, Object> details) {
        try {
            SignatureAuditLog auditLog = new SignatureAuditLog();
            auditLog.setDoctorId(doctorId);
            auditLog.setTokenId(tokenId);
            auditLog.setEventType(event);
            auditLog.setSuccess(success);
            auditLog.setErrorCode(errorCode);
            auditLog.setIpAddress(extractIpAddress());
            auditLog.setUserAgent(extractUserAgent());
            auditLog.setPerformedBy(extractPerformedBy(doctorId));
            auditLog.setPerformedAt(System.currentTimeMillis());

            if (details != null && !details.isEmpty()) {
                auditLog.setDetails(objectMapper.writeValueAsString(details));
            }

            signatureAuditLogRepository.save(auditLog);
        } catch (Exception e) {
            log.error("Signature audit failed for event={} doctorId={} tokenId={}: {}",
                    event, doctorId, tokenId, e.getMessage(), e);
        }
    }

    private String extractIpAddress() {
        HttpServletRequest request = getCurrentRequest();
        if (request == null) return null;

        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null && !forwarded.isBlank()) {
            return forwarded.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    private String extractUserAgent() {
        HttpServletRequest request = getCurrentRequest();
        if (request == null) return null;

        String userAgent = request.getHeader("User-Agent");
        if (userAgent != null && userAgent.length() > 1024) {
            return userAgent.substring(0, 1024);
        }
        return userAgent;
    }

    private String extractPerformedBy(Long doctorId) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getPrincipal() instanceof Jwt) {
                Jwt jwt = (Jwt) auth.getPrincipal();
                return jwt.getSubject();
            }
        } catch (Exception e) {
            // SecurityContext not available
        }

        if (doctorId != null) {
            return "doctor:" + doctorId;
        }

        return "ANONYMOUS";
    }

    private HttpServletRequest getCurrentRequest() {
        try {
            ServletRequestAttributes attrs =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attrs != null) {
                return attrs.getRequest();
            }
        } catch (Exception e) {
            // Not in a request context
        }
        return null;
    }
}
