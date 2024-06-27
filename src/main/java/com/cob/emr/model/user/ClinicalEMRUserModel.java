package com.cob.emr.model.user;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class ClinicalEMRUserModel extends EMRUser {
    private Long id;
    private String fullName;
    private String npi;
    private String licence;
    private String speciality;
    private String credential;
}
