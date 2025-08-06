package com.cob.emr.model.user;

import com.cob.emr.enums.UserType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class EMRUser {
    private String accountName;
    private String firstName;
    private String middleName;
    private String lastName;
    private String password;
    private String email;
    private String uuid;
    private UserType userType;
    private List<EMRUseRoleScope> roleScope;
    private List<Long> clinicIds = new ArrayList<>();
}
