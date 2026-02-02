package com.cob.emr.model.signature;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignatureMetadataDto {
    private String captureMethod;
    private String capturedAt;
    private String userAgent;
    private String screenResolution;
    private Double devicePixelRatio;
    private String platform;
    private String sha256Hash;
    private String fontFamily;
}
