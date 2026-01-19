package com.cob.emr.model.user;

import com.cob.emr.model.clinic.ClinicModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class LoggedInUser {
    private String uuid;
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private Long organizationId;
    private List<EMRUseRoleScope> userRoleScope;
    private List<ClinicModel> clinics;
    private ProviderInfo providerInfo;
}
