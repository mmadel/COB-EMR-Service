package com.cob.emr.usecases.signature;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SignatureEmailService {
    @Value("${app.signature.base-url}")
    private String baseUrl;

    public void sendSignatureLink(String email, String doctorName, String token) {
        String signatureUrl = baseUrl + "/emr-signature/capture?token=" + token;
        log.info("Signature capture link for Dr. {} ({}): {}", doctorName, email, signatureUrl);
    }
}
