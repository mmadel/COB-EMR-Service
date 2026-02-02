package com.cob.emr.model.signature;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignatureSubmitRequest {
    private String signatureData;
    private SignatureMetadataDto metadata;
    private ConsentAcknowledgmentDto consent;
}
