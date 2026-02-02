package com.cob.emr.model.signature;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class SignatureTokenValidationResponse {
    private boolean valid;
    private String doctorName;
    private String doctorEmail;
    private String purpose;
    private LocalDateTime expiresAt;
}
