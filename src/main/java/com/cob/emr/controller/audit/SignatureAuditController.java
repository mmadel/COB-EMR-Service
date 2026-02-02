package com.cob.emr.controller.audit;

import com.cob.emr.exception.business.UserException;
import com.cob.emr.response.ResponseHandler;
import com.cob.emr.usecases.audit.FindSignatureAuditByUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/signature-audit")
@PreAuthorize("hasAnyRole(@role.ADMIN_ROLE)")
public class SignatureAuditController {

    @Autowired
    private FindSignatureAuditByUserUseCase findSignatureAuditByUserUseCase;

    @GetMapping("/find/user/{uuid}")
    public ResponseEntity<Object> findByUser(@PathVariable("uuid") String uuid) throws UserException {
        return ResponseHandler.generateResponse(
                "Successfully retrieved signature audit logs!",
                HttpStatus.OK,
                findSignatureAuditByUserUseCase.find(uuid));
    }
}
