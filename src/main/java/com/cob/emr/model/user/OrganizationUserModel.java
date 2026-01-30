package com.cob.emr.model.user;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OrganizationUserModel {
    private String userName;
    private Long id;
    private String uuid;
    private List<Long> clinicIds;
}
