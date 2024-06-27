package com.cob.emr.model.organization;

import com.cob.emr.model.user.EMRUser;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OrganizationRequest {
    private Long id;
    private String orgname;
    private String dba;
    private String groupNPI;
    private String taxID;
    private String firstAddress;
    private String secondAddress;
    private String city;
    private String state;
    private String zipCode;
    private List<OrganizationClinicsInformation> clinics;
    private EMRUser administrator;
    private List<EMRUser> users;
    private String loggedInUUID;
}
