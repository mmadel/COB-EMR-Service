package com.cob.emr.model.user;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class ClericalEMRUserModel extends EMRUser {
    private Long id;
    private String accountName;
    private String fullName;
    private List<Long> clinicIds = new ArrayList<>();
}
