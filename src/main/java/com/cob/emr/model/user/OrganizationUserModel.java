package com.cob.emr.model.user;

import com.cob.emr.enums.UserStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OrganizationUserModel {
    private String userName;
    private String email;
    private String userType;
    private Long id;
    private String uuid;
    private UserStatus status;
    private List<Long> clinicIds;
}
