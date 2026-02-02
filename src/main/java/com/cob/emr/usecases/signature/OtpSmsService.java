package com.cob.emr.usecases.signature;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OtpSmsService {

    public void sendOtp(String phone, String code, String doctorName) {
        // TODO: replace with real SMS provider integration
        log.info("[MOCK SMS] Sending OTP {} to phone {} for Dr. {}", code, phone, doctorName);
    }
}
