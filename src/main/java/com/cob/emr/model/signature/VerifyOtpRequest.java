package com.cob.emr.model.signature;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyOtpRequest {
    private String uuid;
    private String code;
    private ConsentAcknowledgmentDto consent;
}
