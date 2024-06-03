package com.cob.emr.model.user;

import lombok.Getter;
import lombok.Setter;

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
    private List<EMRUseRoleScope> roleScope;
}
