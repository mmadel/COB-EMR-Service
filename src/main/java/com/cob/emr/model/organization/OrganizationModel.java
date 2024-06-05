package com.cob.emr.model.organization;

import com.cob.emr.model.clinic.ClinicDataHolder;
import com.cob.emr.model.common.AddressDependencyModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class OrganizationModel {

    private Long id;
    private String name;
    private String dba;
    private String groupNPI;
    private String taxID;
    private AddressDependencyModel billingAddress;
    private List<ClinicDataHolder> clinics = new ArrayList<>();
}
