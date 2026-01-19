package com.cob.emr.model.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ProviderInfo {
    private String npi;
    private String credential;
    private String license;
    private String speciality;
}
