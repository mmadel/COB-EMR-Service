package com.cob.emr.model.organization;

import com.cob.emr.model.clinic.ClinicModel;
import com.cob.emr.model.user.EMRUser;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OrganizationRequest {
    private Long id;
    private OrganizationEssentialInformation essential;
    private List<OrganizationClinicsInformation> clinics;
    private EMRUser administrator;
    private List<EMRUser> users;
}
