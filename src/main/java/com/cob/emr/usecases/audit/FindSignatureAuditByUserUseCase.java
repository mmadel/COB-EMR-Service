package com.cob.emr.usecases.audit;

import com.cob.emr.entity.audit.SignatureAuditLog;
import com.cob.emr.entity.security.user.ClinicalEMRUser;
import com.cob.emr.exception.business.UserException;
import com.cob.emr.repositories.audit.SignatureAuditLogRepository;
import com.cob.emr.repositories.security.ClinicalEMRUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindSignatureAuditByUserUseCase {

    @Autowired
    private ClinicalEMRUserRepository clinicalEMRUserRepository;

    @Autowired
    private SignatureAuditLogRepository signatureAuditLogRepository;

    public List<SignatureAuditLog> find(String uuid) throws UserException {
        ClinicalEMRUser user = clinicalEMRUserRepository.findByUUID(uuid)
                .orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND,
                        UserException.USER_NOT_FOUND, new Object[]{}));
        return signatureAuditLogRepository.findByDoctorIdOrderByPerformedAtDesc(user.getId());
    }
}
