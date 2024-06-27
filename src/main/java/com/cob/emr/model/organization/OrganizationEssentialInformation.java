package com.cob.emr.model.organization;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrganizationEssentialInformation {
    private String orgname;
    private String dba;
    private String groupNPI;
    private String taxID;
    private String firstAddress;
    private String secondAddress;
    private String city;
    private String state;
    private String zipCode;
}
