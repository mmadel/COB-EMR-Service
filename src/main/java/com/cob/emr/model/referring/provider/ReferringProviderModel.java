package com.cob.emr.model.referring.provider;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReferringProviderModel {
    private Long id;
    private String firstName;
    private String lastName;
    private String npi;
    private Long organizationId;
}
